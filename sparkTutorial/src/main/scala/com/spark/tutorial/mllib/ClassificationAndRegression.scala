package com.spark.tutorial.mllib

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object ClassificationAndRegression extends App {

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

}
