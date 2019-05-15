package com.spark.tutorial

import org.apache.spark.{SparkConf, SparkContext}

object WordCount extends App {

  val conf = new SparkConf().setMaster("local").setAppName("Test")
  val sc = new SparkContext(conf)
  conf.set("spark.hadoop.validateOutputSpecs", "false")

  val input = sc.textFile("loremIpsum.txt")

  val words = input.flatMap(line => line.split(" "))
  val counts = words
    .map(word => (word, 1))
    .reduceByKey {
      case (x, y) => x + y
    }
    .sortBy(_._2, false)

  // prefer to avoid the chances to overwrite file
  counts.saveAsTextFile("output")

  // load and test println
  val loremIpsum = sc.textFile("output")
  loremIpsum.foreach { println }

}
