package com.spark.tutorial.mllib

import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.linalg.{Matrices, Matrix, Vector, Vectors}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

object VectorsTesting extends App {

  var spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Testing Vector possibility")
    .getOrCreate()

  // Dense Vectors
  println("Dense Vectors")
  val vec: Vector = Vectors.dense(1.0, 2.0, 2.0)
  println(vec)
  println(vec.compressed) // either Sparse or Dense vector

  // find index with max value
  println(s"MAX index = ${vec.argmax}")
  // norm
  println(Vectors.norm(vec, 2))

  val vec2 = Vectors.dense(2.0, 2.0, 2.0)
  // squared distance between two Vectors
  println(Vectors.sqdist(vec, vec2))

  // Sparse Vectors
  println("Sparse Vectors")
  // instead of storing all values of vector
  // sparse vector has fields
  // - size of vector
  // - indices where values aren't zero
  // - values
  // https://databricks.com/blog/2014/07/16/new-features-in-mllib-in-spark-1-0.html
  val v1 = Vectors.dense(1.0, 0.0, 0.0, 3.0)
  // will be sparse(4, [0, 3], [1.0, 3.0])
  println(v1.toSparse)

  val v3 = Vectors.sparse(4, Seq((0, 1.0), (1, 2.0), (3, 3.0)))
  println(v3.toDense)

  // Labeled Point (label, features)
  println("Labeled Point")
  val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))
  val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))
  println(pos)

  // Matrix
  println("Matrix")
  val dm: Matrix = Matrices.dense(2, 2, Array(1.0, 0.0, 0.0, 1.0))
  println(dm)
  val sm: Matrix =
    Matrices.sparse(3, 2, Array(0, 1, 3), Array(0, 2, 1), Array(9, 6, 8))

}
