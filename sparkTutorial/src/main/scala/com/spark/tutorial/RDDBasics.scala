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

  val numbers = (1 to 100).toList.map(e => e.toDouble)
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

  // flatMap
  val words = List("Hello", "World", "How are you ?")
  val wordSplit = sc.parallelize(words).flatMap(word => word.split(" "))
  wordSplit.collect().foreach { println }

  // average of numbers
  val nums = (1 to 100).toList
  val result = sc
    .parallelize(nums)
    .aggregate((0, 0))(
      (acc, value) => (acc._1 + value, acc._2 + 1),
      (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )
  val avg = result._1 / result._2.toDouble
  println(s"Average = ${avg}")

  val rddNums = sc.parallelize(nums)
  // top
  val topNums = rddNums.top(10)
  println("Top numbers:")
  topNums.foreach { println }

  // takeOrdered
  val takeOrdered = rddNums.takeOrdered(5)(Ordering[Int].reverse)
  println("Take ordered:")
  takeOrdered.foreach { println }

  // takeSample
  val takeSample = rddNums.takeSample(withReplacement = false, 10)
  println("Take sample without replacement:")
  takeSample.foreach { println }

  // reduce = sum
  val sumNums = rddNums.reduce(_ + _)
  println(s"Sum of 1-100 = ${sumNums}")

}
