package com.tutorial.chapter3

object FunctionCurring extends App {
  def totalCost(donutType: String)(quantity: Int)(discount: Double): Double = {
    println(s"donutType = $donutType")
    val totalCost = 2.5 * quantity
    totalCost - (totalCost * discount)
  }

  val donut = "My donut"
  val quantity = 2
  val discount = 0.3

  // curried parameter groups
  val totalCost: Double = totalCost(donut)(quantity)(discount)
  println(totalCost)

  // create partially applied function from a function with curried parameters
  val totalCostForVanillaDonut = totalCost("Vanilla") _

  val t2 = totalCostForVanillaDonut(quantity)(discount + 0.1)
  println(s"t2 = $t2")

}
