package com.spark.tutorial

import org.apache.spark.sql.SparkSession

object Accumulators extends App {

  val spark = SparkSession
    .builder()
    .master("local")
    .appName("Accumulators")
    .getOrCreate()

  val sc = spark.sparkContext

  val input = sc.textFile("loremIpsum.txt")

  // count blank line in file
  val blankLines = sc.longAccumulator("Counter")

  val callSigns = input.flatMap { line =>
    if (line == "")
      blankLines.add(1L)
    line.split(" ")
  }
  callSigns.saveAsTextFile("accumulatorOutput")
  println(s"Blank lines: ${blankLines.value}")

}
