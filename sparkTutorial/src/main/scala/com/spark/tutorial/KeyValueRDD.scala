package com.spark.tutorial

import org.apache.spark.{SparkConf, SparkContext}

object KeyValueRDD extends App {
  var conf = new SparkConf().setMaster("local").setAppName("KeyValueRDD")
  val sc = new SparkContext(conf)

  var data = List((1, 2), (3, 4), (3, 6))
  var rdd = sc.parallelize(data)

  // reduceByKey
  var reduceByKey = rdd.reduceByKey((x, y) => x + y)
  reduceByKey.collect().foreach { println }

  // groupByKey
  rdd.groupByKey().collect().foreach { i =>
    println(s"${i._1}, ${i._2}")
  // i._2 = CompactBuffer -> similar to ArrayBuffer ( mutable )  but more memory-efficient
  }

  // mapValues
  rdd.mapValues(x => x * 2).collect().foreach { println }

  // flatMapValues
  rdd.flatMapValues(x => x to 5).foreach { println }

  // keys and values
  val keys = rdd.keys.collect()
  val values = rdd.values.collect()
  println(s"Keys = ${keys.mkString(" ")}, values = ${values.mkString(" ")}")

  // create second rdd and test pair function
  val other = sc.parallelize(List((3, 9)))

  // subtractByKey
  rdd.subtract(other).foreach { println }

  // join
  rdd.join(other).foreach { println }

  // rightOuterJoin
  rdd.rightOuterJoin(other).foreach { println }

  // leftOuterJoin
  rdd.leftOuterJoin(other).foreach { println }

  // cogroup
  rdd.cogroup(other).foreach { println }
}
