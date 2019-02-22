package com.spark.tutorial.mllib

import org.apache.spark.ml.linalg.{Matrix, Vectors, Vector}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.stat.{ChiSquareTest}
import org.apache.spark.ml.stat.Summarizer._

object Basics extends App {

  var spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Basic statistics")
    .getOrCreate()

  // https://spark.apache.org/docs/latest/ml-statistics.html
  val data = Seq(
    Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
    Vectors.dense(4.0, 5.0, 0.0, 3.0),
    Vectors.dense(6.0, 7.0, 0.0, 8.0),
    Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
  )

  val sqlContext = spark.sqlContext
  import sqlContext.implicits._ // for `toDF` method
  val df = data.map(Tuple1.apply).toDF("features")

  val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
  println(s"Pearson correlation matrix:\n${coeff1}")

  val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
  println(s"Spearman correlation matrix:\n${coeff2}")

  // Hypothesis testing
  val data1 = Seq(
    (0.0, Vectors.dense(0.5, 10.0)),
    (0.0, Vectors.dense(1.5, 20.0)),
    (1.0, Vectors.dense(1.5, 30.0)),
    (0.0, Vectors.dense(3.5, 30.0)),
    (0.0, Vectors.dense(3.5, 40.0)),
    (1.0, Vectors.dense(3.5, 40.0))
  )

  val df1 = data1.toDF("label", "features")
  val chi = ChiSquareTest.test(df1, "features", "label").head
  println(s"pValues = ${chi.getAs[Vector](0)}")
  println(s"degreeOfFreedom = ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
  println(s"statistics = ${chi.getAs[Vector](2)}")

  // Summarizer
  val data2 = Seq(
    (Vectors.dense(2.0, 3.0, 5.0), 1.0),
    (Vectors.dense(4.0, 6.0, 7.0), 2.0)
  )
  val df2 = data2.toDF("features", "weight")
  val (meanVal, varianceVal) = df2
    .select(
      metrics("mean", "variance").summary($"features", $"weight").as("summary"))
    .select("summary.mean", "summary.variance")
    .as[(Vector, Vector)]
    .first()
  println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")

  val (meanVal2, varianceVal2) = df
    .select(mean($"features"), variance($"features"))
    .as[(Vector, Vector)]
    .first()
  println(s"without weight: mean = ${meanVal2}, variance = ${varianceVal2}")

}
