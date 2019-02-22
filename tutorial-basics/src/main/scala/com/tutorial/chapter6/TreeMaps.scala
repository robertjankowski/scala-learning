package com.tutorial.chapter6

import scala.collection.immutable.TreeMap

object TreeMaps extends App {

  type Drink = (Int, String)
  val treeMap =
    TreeMap[String, Drink]("Rob" -> (10, "Tea"), "Bob" -> (20, "Coffee"))

  treeMap.foreach {
    println
  }

  // access element
  println(s"Rob drink: ${treeMap("Rob")}")

  // add element
  val treeMap1 = treeMap + ("Jessi" -> (10, "Bob"))

  object AlphabetOrdering extends Ordering[String] {
    override def compare(x: String, y: String): Int = {
      x.compareTo(y)
    }
  }

  val treeDiffOrder =
    TreeMap[String, String]("c" -> "C", "b" -> "B", "f" -> "f")(
      AlphabetOrdering)
  treeDiffOrder.foreach {
    println
  }

}
