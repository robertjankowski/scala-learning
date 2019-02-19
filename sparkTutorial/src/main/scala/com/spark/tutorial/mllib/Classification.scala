package com.spark.tutorial.mllib

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification._
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Classification extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Classification and regression")
    .getOrCreate()

  // -------------
  // Binomial logistic regression
  val training = spark.read.format("libsvm").load("sample_libsvm_data.txt")
  val lr = new LogisticRegression()
    .setMaxIter(10)
    .setRegParam(0.3)
    .setElasticNetParam(0.8)
  val lrModel = lr.fit(training)
  println(
    s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

  // another options
  val mlr = new LogisticRegression()
    .setMaxIter(10)
    .setRegParam(0.3)
    .setElasticNetParam(0.8)
    .setFamily("multinomial")
  val mlrModel = mlr.fit(training)
  println(s"Multinomial coefficients: ${mlrModel.coefficientMatrix}")
  println(s"Multinomial intercepts: ${mlrModel.interceptVector}")

  val trainingSummery = lrModel.binarySummary
  val objectiveHistory = trainingSummery.objectiveHistory
  println("ObjectiveHistory:")
  objectiveHistory.foreach { loss =>
    println(loss)
  }
  val roc = trainingSummery.roc
  roc.show()
  println(s"areaUnderRoc: ${trainingSummery.areaUnderROC}")

  // Set the model threshold to maximize F-Measure
  val fMeasure = trainingSummery.fMeasureByThreshold
  val maxFMeasure = fMeasure.select(max("F-Measure")).head().getDouble(0)
//  val bestThreshold = fMeasure
//    .where($"F-Measure" === maxFMeasure)
//    .select("threshold")
//    .head()
//    .getDouble(0)
//  lrModel.setThreshold(bestThreshold)

  // -------------
  // Decision Tree Classifier
  val labelIndexer = new StringIndexer()
    .setInputCol("label")
    .setOutputCol("indexedLabel")
    .fit(training)
  val featureIndexer = new VectorIndexer()
    .setInputCol("features")
    .setOutputCol("indexedFeatures")
    .setMaxCategories(4)
    .fit(training)
  val Array(trainingData, testdata) = training.randomSplit(Array(0.7, 0.3))
  val dt = new DecisionTreeClassifier()
    .setLabelCol("indexedLabel")
    .setFeaturesCol("indexedFeatures")
  val labelConverter = new IndexToString()
    .setInputCol("prediction")
    .setOutputCol("predictedLabel")
    .setLabels(labelIndexer.labels)
  val pipeline = new Pipeline()
    .setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

  val model = pipeline.fit(trainingData)
  val predictions = model.transform(testdata)

  predictions.select("predictedLabel", "label", "features").show(5)
  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("indexedLabel")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val accuracy = evaluator.evaluate(predictions)
  println(s"Test Error = ${1.0 - accuracy}")

  val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
  println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

  // RandomForest and GradientBoostedTreeClassifier are almost the same
  // except different model

  // -------------
  // Multilayer perceptron classifier
  val data1 = spark.read
    .format("libsvm")
    .load("sample_multiclass_classification_data.txt")
  val splits = data1.randomSplit(Array(0.6, 0.4), seed = 1234L)
  val train = splits(0)
  val test = splits(1)

  val layers = Array[Int](4, 5, 4, 3)
  val trainer = new MultilayerPerceptronClassifier()
    .setLayers(layers)
    .setBlockSize(128)
    .setSeed(1234L)
    .setMaxIter(100)
  val nn = trainer.fit(train)
  val res = nn.transform(test)
  val predictionAndLabels = res.select("prediction", "label")
  val evaluator1 =
    new MulticlassClassificationEvaluator().setMetricName("accuracy")
  println(s"Test set accuracy: ${evaluator1.evaluate(predictionAndLabels)}")

  // Linear SVM
  val lsvc = new LinearSVC()
    .setMaxIter(10)
    .setRegParam(0.1)
  val lsvcModel = lsvc.fit(trainingData)
  println(
    s"Coefficients: ${lsvcModel.coefficients} Intercept: ${lsvcModel.intercept}")

  //

}
