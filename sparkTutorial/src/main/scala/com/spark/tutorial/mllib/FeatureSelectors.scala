package com.spark.tutorial.mllib

import java.util

import org.apache.spark.ml.attribute.{
  Attribute,
  AttributeGroup,
  NumericAttribute
}
import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions.col

object FeatureSelectors extends App {
  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Feature Selectors")
    .getOrCreate()

  // ----------------
  // VectorSlicer
  val data = util.Arrays.asList(
    Row(Vectors.sparse(3, Seq((0, -2.0), (1, 2.3)))),
    Row(Vectors.dense(-2.0, 2.3, 0.0))
  )
  val defaultAttr = NumericAttribute.defaultAttr
  val attrs = Array("f1", "f2", "f3").map(defaultAttr.withName)
  val attrGroup =
    new AttributeGroup("userFeatures", attrs.asInstanceOf[Array[Attribute]])

  val dataset =
    spark.createDataFrame(data, StructType(Array(attrGroup.toStructField())))
  val slicer =
    new VectorSlicer().setInputCol("userFeatures").setOutputCol("features")
  slicer.setIndices(Array(1)).setNames(Array("f3"))
  val output = slicer.transform(dataset)
  output.show(false)

  // ----------------
  // RFormula
  val dataset1 = spark
    .createDataFrame(
      Seq(
        (7, "US", 18, 1.0),
        (8, "CA", 12, 0.0),
        (9, "NZ", 15, 0.0)
      ))
    .toDF("id", "country", "hour", "clicked")
  val formula = new RFormula()
    .setFormula("clicked ~ country + hour")
    .setFeaturesCol("features")
    .setLabelCol("label")
  val output1 = formula.fit(dataset1).transform(dataset1)
  output1.select("features", "label", "clicked").show(false)

  // ----------------
  // ChiSqSelector
  val data1 = Seq(
    (7, Vectors.dense(0.0, 0.0, 18.0, 1.0), 1.0),
    (8, Vectors.dense(0.0, 1.0, 12.0, 0.0), 0.0),
    (9, Vectors.dense(1.0, 0.0, 15.0, 0.1), 0.0)
  )
  val df = spark.createDataFrame(data1).toDF("id", "features", "clicked")
  val selector = new ChiSqSelector()
    .setNumTopFeatures(1)
    .setFeaturesCol("features")
    .setLabelCol("clicked")
    .setOutputCol("selectedFeatures")
  val result1 = selector.fit(df).transform(df)
  result1.show(false)

  // ----------------
  // Locality Sensitive Hashing
  // Bucketed Random Projection For Euclidean Distance
  val dfA = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 1.0)),
        (1, Vectors.dense(1.0, -1.0)),
        (2, Vectors.dense(-1.0, -1.0)),
        (3, Vectors.dense(-1.0, 1.0))
      ))
    .toDF("id", "features")

  val dfB = spark
    .createDataFrame(
      Seq(
        (4, Vectors.dense(1.0, 0.0)),
        (5, Vectors.dense(-1.0, 0.0)),
        (6, Vectors.dense(0.0, 1.0)),
        (7, Vectors.dense(0.0, -1.0))
      ))
    .toDF("id", "features")
  val key = Vectors.dense(1.0, 0.0)
  val brp = new BucketedRandomProjectionLSH()
    .setBucketLength(2.0)
    .setNumHashTables(3)
    .setInputCol("features")
    .setOutputCol("hashes")
  val model = brp.fit(dfA)
  println(
    "The hashes dataset where hashed values are stored in the column `hashes`:")
  model.transform(dfA).show(false)
  println(
    "Approximately joining dfA and dfB on Euclidean Distance smaller than 1.5:")
  model
    .approxSimilarityJoin(dfA, dfB, 1.5, "EuclideanDistance")
    .select(col("datasetA.id").alias("idA"),
            col("datasetB.id").alias("idB"),
            col("EuclideanDistance"))
    .show(false)

  println("Approximately searching dfA for 2 nearest neighbors of the key:")
  model.approxNearestNeighbors(dfA, key, 2).show(false)

  // MinHash for Jaccard Distance
  val dfA1 = spark
    .createDataFrame(
      Seq(
        (0, Vectors.sparse(6, Seq((0, 1.0), (1, 1.0), (2, 1.0)))),
        (1, Vectors.sparse(6, Seq((2, 1.0), (3, 1.0), (4, 1.0)))),
        (2, Vectors.sparse(6, Seq((0, 1.0), (2, 1.0), (4, 1.0))))
      ))
    .toDF("id", "features")

  val dfB1 = spark
    .createDataFrame(
      Seq(
        (3, Vectors.sparse(6, Seq((1, 1.0), (3, 1.0), (5, 1.0)))),
        (4, Vectors.sparse(6, Seq((2, 1.0), (3, 1.0), (5, 1.0)))),
        (5, Vectors.sparse(6, Seq((1, 1.0), (2, 1.0), (4, 1.0))))
      ))
    .toDF("id", "features")

  val key1 = Vectors.sparse(6, Seq((1, 1.0), (3, 1.0)))
  val mh = new MinHashLSH()
    .setNumHashTables(5)
    .setInputCol("features")
    .setOutputCol("hashes")
  val model1 = mh.fit(dfA1)
  println(
    "The hashes dataset where hashed values are stored in the column `hashes`:")
  model1.transform(dfA1).show(false)

  println(
    "Approximately joining dfA and dfB on Euclidean Distance smaller than 0.6:")
  model1
    .approxSimilarityJoin(dfA1, dfB1, 0.6, "JaccardDistance")
    .select(col("datasetA.id").alias("idA"),
            col("datasetB.id").alias("idB"),
            col("JaccardDistance"))
    .show(false)

  println("Approximately searching dfA for 2 nearest neighbors of the key:")
  model1.approxNearestNeighbors(dfA1, key1, 2).show(false)

}
