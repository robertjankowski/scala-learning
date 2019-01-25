package com.tutorial.chapter3

import scala.math.sqrt

object FunctionAsParameters extends App {

  def totalCost(donutType: String)(quantity: Int)(
      f: Double => Double): Double = {
    println(donutType)
    val total = 2.5 * quantity
    f(total)
  }

  val t = totalCost("Vanilla")(10)(total => {
    total / 2
  })
  println(t)

  def discount(d: Double): Double = {
    sqrt(d + 10)
  }

  val t2 = totalCost("Vanilla2")(20)(discount)
  println(t2)

}
