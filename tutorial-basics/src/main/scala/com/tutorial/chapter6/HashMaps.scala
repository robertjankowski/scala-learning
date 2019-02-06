package com.tutorial.chapter6

import scala.collection.immutable.HashMap

object HashMaps extends App {

  val hashMap1 = HashMap[String, Int]("Mike" -> 12,
                                      "Tom" -> 10,
                                      "Jerry" -> 20,
                                      "Marry" -> 99)
  hashMap1.foreach { map =>
    println(s"Key: ${map._1}, value: ${map._2}")
  }

  // add element
  val hashMap2 = hashMap1 + ("Jake" -> 29)

  // get difference between maps
  val diffs1 = (hashMap2.toSet.diff(hashMap1.toSet)).toMap
  diffs1.foreach {
    println
  }
  println()

  // remove HashMap
  val hashMap3 = hashMap2 -- Seq("Tom", "Marry")
  hashMap3.foreach {
    println(_)
  }

  // empty map
  val empty = HashMap.empty[String, Int]

}
