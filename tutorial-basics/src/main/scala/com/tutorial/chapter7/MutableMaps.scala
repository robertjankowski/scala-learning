package com.tutorial.chapter7

import scala.collection.mutable
import scala.collection.mutable.{HashMap, ListMap, Map}

object MutableMaps extends App {

  val map1 = Map("One" -> 1, "Two" -> 2, "Three" -> 3)
  println(s"elements of map $map1")

  // add element
  map1 += ("Four" -> 4)
  map1 ++= Map("Five" -> 5, "Six" -> 6)

  // remove element
  map1 -= "Two"
  map1.foreach { println }

  // HASH MAP
  val hashMap1 = HashMap("One" -> 1, "Two" -> 2, "Three" -> 3)
  println(s"Value at one: ${hashMap1("One")}")

  // LIST MAP
  val listMap1 = ListMap("One" -> 1, "Two" -> 2, "Three" -> 3)
  listMap1 --= List("One", "Two")
  println(s"${listMap1.contains("Three")}")
  val t = listMap1.foldLeft(0)(_ + _._2)
  println(s"sum of values $t")

  // Linked Hash Map
  val linkedHashMap =
    mutable.LinkedHashMap("One" -> 1.0, "Two" -> 2.0, "Three" -> 3.0)
  println(linkedHashMap("One"))
  linkedHashMap += ("Four" -> 4.0)
  linkedHashMap ++= linkedHashMap
  linkedHashMap.foreach { println }
  val empty = mutable.LinkedHashMap.empty[String, Stream[Int]]
}
