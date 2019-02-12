package com.spark.tutorial

import org.apache.spark.sql.SparkSession

object SparkSQL extends App {

  val spark =
    SparkSession.builder().master("local[*]").appName("Spark SQL").getOrCreate()

  val sqlContext = spark.sqlContext

  val input = spark.read.option("multiline", true).json("sample.json")
  input.printSchema()

  input.createOrReplaceTempView("sample")
  val res = sqlContext.sql("SELECT * FROM sample")
  res.show(false)

  val before2010 =
    sqlContext.sql(
      "SELECT sample.Count, sample.County FROM sample WHERE sample.Year < 2010")
  before2010.show(false)

  val count = input.rdd
    .map { row =>
      val r = row.getString(0)
      print(r)
      (r.toInt, 1)
    }
    .reduceByKey((x, y) => x + y)
  // sum of count
  count.foreach { println }

  // from rdd
  case class Donut(name: String, quality: Int)
  val donutRDD = spark.sparkContext.parallelize(
    List(Donut("Plain", 10), Donut("Vanilla", 30), Donut("Chocolate", 31)))

  val donutSQL = sqlContext.createDataFrame(donutRDD)
  donutSQL.createOrReplaceTempView("donut")
  donutSQL.show(false)

}
