package com.spark.tutorial

import org.apache.spark.sql.SparkSession

object Stats extends App {

  val spark = SparkSession
    .builder()
    .appName("Basic statistics")
    .master("local[*]") // all cores
    .getOrCreate()

  val sc = spark.sparkContext

  // sample data
  val input = sc.textFile("hollins.dat")
  val data = input.map { line =>
    val l = line.split(" ")
    l.last.toDouble
  }
//  data.foreach { case e => print(s"$e, ") }
  val stats = data.stats()
  println(s"MAX = ${stats.max}")
  println(s"MIN = ${stats.min}")
  println(s"MEAN = ${stats.mean}")
  println(s"SD = ${stats.stdev}")

}
