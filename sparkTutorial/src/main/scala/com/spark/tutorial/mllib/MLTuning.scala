package com.spark.tutorial.mllib

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.{
  BinaryClassificationEvaluator,
  RegressionEvaluator
}
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.tuning.{
  CrossValidator,
  ParamGridBuilder,
  TrainValidationSplit
}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.regression.LinearRegression

object MLTuning extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("ML Tuning: model selection and hyperparameter tuning")
    .getOrCreate()

  // ----------
  // Cross-Validation
  // Prepare training data from a list of (id, text, label) tuples.
  val training = spark
    .createDataFrame(
      Seq(
        (0L, "a b c d e spark", 1.0),
        (1L, "b d", 0.0),
        (2L, "spark f g h", 1.0),
        (3L, "hadoop mapreduce", 0.0),
        (4L, "b spark who", 1.0),
        (5L, "g d a y", 0.0),
        (6L, "spark fly", 1.0),
        (7L, "was mapreduce", 0.0),
        (8L, "e spark program", 1.0),
        (9L, "a e c l", 0.0),
        (10L, "spark compile", 1.0),
        (11L, "hadoop software", 0.0)
      ))
    .toDF("id", "text", "label")

  // pipeline= tokenizer -> hashingTF -> lr
  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  val hashingTF =
    new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  val lr = new LogisticRegression().setMaxIter(10)
  val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, lr))

  val paramGrid = new ParamGridBuilder()
    .addGrid(hashingTF.numFeatures, Array(10, 100, 1000))
    .addGrid(lr.regParam, Array(0.1, 0.01))
    .build()

  val cv = new CrossValidator()
    .setEstimator(pipeline)
    .setEvaluator(new BinaryClassificationEvaluator())
    .setEstimatorParamMaps(paramGrid)
    .setNumFolds(3)
    .setParallelism(2)

  val cvModel = cv.fit(training)

  val test = spark
    .createDataFrame(
      Seq(
        (4L, "spark i j k"),
        (5L, "l m n"),
        (6L, "mapreduce spark"),
        (7L, "apache hadoop")
      ))
    .toDF("id", "text")

  cvModel
    .transform(test)
    .select("id", "text", "probability", "prediction")
    .collect()
    .foreach {
      case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
        println(s"($id, $text) --> prob $prob, prediction: $prediction:")
    }

  // ----------
  // Train-Validation Split
  val data =
    spark.read.format("libsvm").load("sample_linear_regression_data.txt")
  val Array(trainingDF, testDF) = data.randomSplit(Array(0.9, 0.1), seed = 42L)
  val linReg = new LinearRegression().setMaxIter(10)

  val paramGrid2 = new ParamGridBuilder()
    .addGrid(linReg.regParam, Array(0.1, 0.01))
    .addGrid(linReg.fitIntercept)
    .addGrid(linReg.elasticNetParam, Array(0.0, 0.5, 1.0))
    .build()

  val trainValidationSplit = new TrainValidationSplit()
    .setEstimator(linReg)
    .setEvaluator(new RegressionEvaluator())
    .setEstimatorParamMaps(paramGrid2)
    .setTrainRatio(0.8)
    .setParallelism(2)

  val model = trainValidationSplit.fit(trainingDF)
  model
    .transform(testDF)
    .select("features", "label", "prediction")
    .show()

}
