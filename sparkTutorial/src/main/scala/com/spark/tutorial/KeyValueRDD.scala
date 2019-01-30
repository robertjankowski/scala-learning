package com.spark.tutorial

import org.apache.spark.rdd.RDD
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

  /*
  Aggregate key-value
  - reduceByKey
  - foldByKey
  - mapValues
   */
  val aRDD = sc.parallelize(
    List(("panda", 2), ("pirate", 3), ("panda", 4), ("pirate", 2)))
  // average by key
  aRDD
    .mapValues(x => (x, 1))
    .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)) // ("name", (sum[int], count[int]))
    .mapValues(x => x._1 / x._2.toDouble) // ("name", average)
    .foreach { println }

  // parallel level
  val testData = Seq(("a", 1), ("b", 23), ("a", 2), ("c", 10))
  val testRDD = sc.parallelize(testData)
  testRDD.reduceByKey((x, y) => x + y, 10).foreach { println }

  // Grouping data
  val g1 = testRDD.groupByKey(3).collect()
  val g2 = testRDD.groupBy(_._1).map(x => (x._1, x._2.map(y => y._2))).collect() // maybe it's another way

  // Joining
  case class Store(name: String)
  val storeAddress = Seq(
    (Store("One"), "1 street"),
    (Store("Two"), "2 street"),
    (Store("One"), "3 street")
  )
  val storeRating = Seq(
    (Store("One"), 23.4),
    (Store("Two"), 23)
  )
  // join (also leftOuterJoin, rightOuterJoin)
  sc.parallelize(storeAddress).join(sc.parallelize(storeRating)).foreach {
    println
  }

  // sorting
  val nums = sc.parallelize(1 to 100)
  implicit val sortIntByString = new Ordering[Int] {
    override def compare(x: Int, y: Int): Int = x.toString.compareTo(y.toString)
  }
  nums.sortBy(x => x).foreach { println }

  // partitioner
  val pairs = sc.parallelize(List((1, 1), (2, 2), (3, 3)))
  import org.apache.spark.HashPartitioner
  println(pairs.partitioner) // None
  val partitioned = pairs.partitionBy(new HashPartitioner(2))
  println(partitioned.partitioner)

}
