package com.tutorial.chapter8

import scala.util.Sorting

object Sort extends App {

  case class Dog(age: Int, color: String)

  val unorderedList =
    Array(Dog(5, "brown"), Dog(4, "black"), Dog(4, "white11"))

  object ownOrdering extends Ordering[Dog] {
    override def compare(x: Dog, y: Dog): Int = {
      val v1 = x.age + x.color.length
      val v2 = y.age + y.color.length
      v1.compare(v2)
    }
  }
  Sorting.quickSort(unorderedList)(ownOrdering)
  unorderedList.foreach { println }

  // sorted
  val l = (1 to 10).toList.reverse
  println(s"Sorted: ${l.reverse.sorted}")

  // sortWith
  l.sortWith((a, b) => 2 * a < b + 2).foreach { e =>
    print(s"$e, ")
  }
}
