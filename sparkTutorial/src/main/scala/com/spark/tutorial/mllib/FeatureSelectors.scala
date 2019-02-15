package com.spark.tutorial.mllib

import java.util

import org.apache.spark.ml.attribute.{
  Attribute,
  AttributeGroup,
  NumericAttribute
}
import org.apache.spark.ml.feature.{ChiSqSelector, RFormula, VectorSlicer}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}

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
  // TODO:
}
