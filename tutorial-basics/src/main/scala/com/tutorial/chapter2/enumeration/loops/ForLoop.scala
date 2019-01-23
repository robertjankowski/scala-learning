package com.tutorial.chapter2.enumeration.loops

object ForLoop extends App {

  // 1 2 3 4 5
  for (n <- 1 to 5) {
    println(f"Number ${n}")
  }

  // 1 2 3 4 not 5
  for (n <- 1 until 5) {
    println(f"N = ${n}")
  }

  // filter values using if in for loop
  val donutList = List("flour", "sugar", "syrop")
  for (i <- donutList if i == "sugar") {
    println(f"Find ${i}")
  }

  val sugar = for {
    i <- donutList
    if i == "sugar" || i == "flour"
  } yield i
  println(sugar)

  // 2d array
  val twoDArray = Array.ofDim[String](2, 2)
  for {
    x <- 0 until twoDArray.size
    y <- 0 until twoDArray.size
  } {
    twoDArray(x)(y) = (x * y).toString()
    println(twoDArray(x)(y))
  }

}
