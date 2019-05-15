package com.tutorial.chapter8

object MaxByMinBy extends App {
  case class Donut(name: String, price: Double)

  val donuts =
    List(Donut("One", 1.0), Donut("Two1234", 23.0), Donut("Three", 0.99))
  println(s"Max by name: ${donuts.maxBy(_.name)}")
  println(s"Max by price: ${donuts.maxBy(_.price)}")

  println(s"Min by name: ${donuts.minBy(_.name)}")
  println(s"Min by price: ${donuts.minBy(_.price)}")

  // mkString
  println(donuts.mkString("|"))
  println(donuts.mkString("Donuts: ", "\t", " are very tasty"))

  // nonEmpty
  println(if (donuts.nonEmpty) "Yes" else "No")
  println(Seq.empty[Int].nonEmpty)

}
