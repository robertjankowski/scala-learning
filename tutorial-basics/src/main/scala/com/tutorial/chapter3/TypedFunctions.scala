package com.tutorial.chapter3

object TypedFunctions extends App {
  def test(test: String) = {
    println(s"String test = $test")
  }

  def test(test: Double) = {
    println(s"Double test = $test")
  }

  test("Boi !")
  test(23.3)
  println()

  // generic typed function
  def gTest[T](test: T) = {
    test match {
      case d: String => println(s"String = $d")
      case d: Int    => println(s"Int = $d")
      case d: Double => println(s"Double = $d")
      case _         => println(s"Unknown type of $test")
    }
  }

  gTest(23)
  gTest("23")
  gTest(2.34)
  gTest(List(2, 34))

}
