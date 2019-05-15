package com.tutorial.chapter8

import scala.util.Sorting

object SortTakeTranspose extends App {

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

  println(s"TakeWhile ${l.takeWhile(_ > 2).mkString(", ")}")
  val odd: Int => Boolean = x => x % 2 == 0
  println(s"to first not odd: ${l.takeWhile(odd)}")
  println(s"Odd: ${l.filter(odd)}")

  // transpose
  val l1 = List("One", "Two", "Three")
  val l2 = (1 to 3).toList
  val ll = List(l1, l2)
  println(ll)
  println(ll.transpose)

  // unzip
  val names = Seq("Rob", "Mark", "Bob")
  def transform(names: Seq[String], a: String => String): Seq[String] = {
    names.map(a)
  }
  val transformedNames = transform(names, _.toLowerCase)
  println(transformedNames.mkString(", "))
  val numbers = 1 to 3
  val namesWithNumbers = names zip numbers
  namesWithNumbers.foreach({
    case (a: String, b: Int) => println(s"Name: $a, number: $b")
  })
  val unzipNamesWithNumbers = namesWithNumbers.unzip

  object Colors extends Enumeration {
    type color = Value
    val BLUE, RED, GREEN, ORANGE = Value
  }
  import Colors._
  val colors = Seq(BLUE, RED, GREEN)
  val colNamesNums = colors zip namesWithNumbers
  colNamesNums.unzip._1.foreach(col => print(col.id))

}
