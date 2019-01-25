package com.spark.tutorial

import org.apache.spark.{SparkConf, SparkContext}
import scala.math.sqrt

object RDDBasics extends App {

  val conf = new SparkConf().setAppName("Basics of RDD").setMaster("local")
  val sc = new SparkContext(conf)

  // create RDD from List
  val l: List[String] = List("Robert", "Mike", "Tom", "Alice")
  val rddFromList = sc.parallelize(l, 2)

  val containsO = rddFromList.filter(e => e.contains('o')).collect()
  containsO.foreach { println } // Robert Tom

  // sqrt root
  def doubleSqrt(x: Double): Double = {
    sqrt(sqrt(x))
  }

  val numbers = (1 to 1000000).toList.map(e => e.toDouble)
  val rddSingle = sc.parallelize(numbers)
  val rddMultiple = sc.parallelize(numbers, 2) // split to 2 pieces

  val t1 = System.nanoTime
  val toSqrtSingle = rddSingle.map(doubleSqrt).collect
  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Duration single: ${duration}, ${toSqrtSingle.head}")

  val t2 = System.nanoTime
  val toSqrtMultiple = rddMultiple.map(doubleSqrt).collect
  val duration2 = (System.nanoTime - t2) / 1e9d
  println(s"Duration multiple: ${duration2}, ${toSqrtMultiple.head}") // faster options !!

}
