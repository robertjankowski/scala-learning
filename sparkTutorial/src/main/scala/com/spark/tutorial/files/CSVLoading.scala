package com.spark.tutorial.files

import org.apache.spark.sql.SparkSession

object CSVLoading extends App {

  val spark = SparkSession
    .builder()
    .master("local")
    .appName("CSV loading app")
    .getOrCreate()

  val input =
    spark.read.format("csv").option("header", true).load("ph-data.csv")

  // it works !!!
  input.show(10)
  input.printSchema()

  // query some results
  import spark.implicits._
  input
    .select("blue")
    .map(row => row.getString(0).toInt)
    .filter(i => i > 100)
    .show()

  // register df as temporary view
  input.createOrReplaceTempView("PH")
  val sqlContext = spark.sqlContext
  val query = sqlContext.sql("SELECT * FROM PH WHERE blue > 100")
  query.show()

  // filter (only even label) and save data
  sqlContext
    .sql("SELECT * FROM PH WHERE label % 2 == 0")
    .rdd
    .saveAsTextFile("ph-even-label")

}
