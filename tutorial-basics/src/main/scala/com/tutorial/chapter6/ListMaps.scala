package com.tutorial.chapter6

import scala.collection.immutable.ListMap

object ListMaps extends App {

  val listMap = ListMap("Rob" -> 1997, "Bob" -> 2044, "Mark" -> 123)
//  listMap.foreach { println }

  val rob = listMap("Rob")

  val listMap2 = listMap + ("Mike" -> 9910)

  // add to ListMaps
  val listMap3 = listMap ++ listMap2
  listMap3.foreach { s =>
    println(s"key: ${s._1}, \tvalue: ${s._2}")
  }

  // remove elements
  val listMap4 = listMap3 -- Seq("Rob", "Bob")
  listMap4.foreach { println }

  // empty ListMap
  type Currency = Double
  val empty = ListMap.empty[String, Currency]

}
