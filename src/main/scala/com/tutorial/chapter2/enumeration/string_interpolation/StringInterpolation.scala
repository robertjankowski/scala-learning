package com.tutorial.chapter2.enumeration.string_interpolation

object StringInterpolation extends App {

  val donut: String = "Donut"
  println(s"I like $donut")

  case class Donut(name: String, testLevel: Int)
  val donut2: Donut = Donut("My Donut", 10)
  println(s"${donut2.name}, tasteLvl: ${donut2.testLevel}")

  val test: Int = 10
  println(s"Test is equal 10? = ${test == 10}")

  println(raw"Test\tTab\tok")

}
