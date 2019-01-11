package com.tutorial.chapter3

class DonutCostCalculator {
  val totalCost = 100

  def minusDiscount(discount: Double): Double = {
    totalCost - discount
  }

  // function with symbol name
  def -(discount: Double): Double = {
    totalCost - discount
  }

  def ***() = "Testing !"

  def ++(cost: Int) = {
    totalCost + cost
  }
}

object FunctionsAsSymbols extends App {
  val donutCostCalculator = new DonutCostCalculator()

  // call method
  println(s"${donutCostCalculator.minusDiscount(10.34)}")

  // call symbol method
  println(s"${donutCostCalculator.-(10.3)}")
  println(s"${donutCostCalculator - 10.3}") // the same

  println(s"${donutCostCalculator ***}")

  println(s"${donutCostCalculator ++ 10}")

}
