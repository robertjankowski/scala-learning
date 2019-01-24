package com.spark.tutorial

import org.apache.spark.SparkContext._
import org.apache.spark.{SparkContext, SparkConf}

object sparkExample extends App {

  val conf = new SparkConf().setMaster("local").setAppName("Test")
  val sc = new SparkContext(conf)

  val rdd: List[String] = List[String]("Rob", "Bob")

  // it's working !!
  println(sc.sparkUser)

}
