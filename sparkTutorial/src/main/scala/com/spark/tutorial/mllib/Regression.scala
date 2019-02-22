package com.spark.tutorial.mllib

import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.regression.{
  AFTSurvivalRegression,
  GeneralizedLinearRegression,
  IsotonicRegression
}
import org.apache.spark.sql.SparkSession

object Regression extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Regression")
    .getOrCreate()

  val dataset =
    spark.read.format("libsvm").load("sample_linear_regression_data.txt")

  // ----------
  // GeneralizedLinearRegression
  val glr = new GeneralizedLinearRegression()
    .setFamily("gaussian")
    .setLink("identity")
    .setMaxIter(10)
    .setRegParam(0.3)

  val model = glr.fit(dataset)
  println(s"Coefficients: ${model.coefficients}")
  println(s"Intercept: ${model.intercept}")

  // Summarize the model over the training set and print out some metrics
  val summary = model.summary
  println(
    s"Coefficient Standard Errors: ${summary.coefficientStandardErrors.mkString(",")}")
  println(s"T Values: ${summary.tValues.mkString(",")}")
  println(s"P Values: ${summary.pValues.mkString(",")}")
  println(s"Dispersion: ${summary.dispersion}")
  println(s"Null Deviance: ${summary.nullDeviance}")
  println(
    s"Residual Degree Of Freedom Null: ${summary.residualDegreeOfFreedomNull}")
  println(s"Deviance: ${summary.deviance}")
  println(s"Residual Degree Of Freedom: ${summary.residualDegreeOfFreedom}")
  println(s"AIC: ${summary.aic}")
  println("Deviance Residuals: ")
  summary.residuals().show()

  // Decision tree, random forest and gb regression method also almost the same
  // ----------
  // Survival regression
  val training = spark
    .createDataFrame(
      Seq(
        (1.218, 1.0, Vectors.dense(1.560, -0.605)),
        (2.949, 0.0, Vectors.dense(0.346, 2.158)),
        (3.627, 0.0, Vectors.dense(1.380, 0.231)),
        (0.273, 1.0, Vectors.dense(0.520, 1.151)),
        (4.199, 0.0, Vectors.dense(0.795, -0.226))
      ))
    .toDF("label", "censor", "features")
  val quantileProbabilities = Array(0.3, 0.6)
  val aft = new AFTSurvivalRegression()
    .setQuantileProbabilities(quantileProbabilities)
    .setQuantilesCol("quantiles")
  val model1 = aft.fit(training)

  println(s"Coefficients: ${model1.coefficients}")
  println(s"Intercept: ${model1.intercept}")
  println(s"Scale: ${model1.scale}")
  model1.transform(training).show(false)

  // ----------
  // Isotonic regression
  val dataset1 = spark.read
    .format("libsvm")
    .load("sample_isotonic_regression_libsvm_data.txt")
  val ir = new IsotonicRegression()
  val model2 = ir.fit(dataset1)
  println(s"Boundaries in increasing order: ${model2.boundaries}")
  println(s"Predictions associated with the boundaries: ${model2.predictions}")
  model2.transform(dataset1).show()

}
