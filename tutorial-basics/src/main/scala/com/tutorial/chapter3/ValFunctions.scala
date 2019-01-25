package com.tutorial.chapter3

object ValFunctions extends App {

  def totalCostWithDiscountFunctionParameter(donutType: String)(quantity: Int)(
      f: Double => Double): Double = {
    println(s"Calculating total cost for $quantity $donutType")
    val totalCost = 2.50 * quantity
    f(totalCost)
  }

  def applyDiscount(totalCost: Double): Double = {
    val discount = 2 // assume you fetch discount from database
    totalCost - discount
  }

  println(s"""
       |Total cost of 5 Glazed Donuts with discount def function = ${totalCostWithDiscountFunctionParameter(
               "Glazed Donut"
             )(5)(applyDiscount(_))}
     """.stripMargin)

  // create function using the `val` keyword instead of `def`
  val applyDiscountValueFunction = (totalCost: Double) => {
    val discount = 2
    totalCost - discount
  }
  // another solution
  val testFunc: Double => Double = totalCost => {
    val discount = 40
    totalCost - discount
  }

  val totalCostValue = totalCostWithDiscountFunctionParameter("Donut")(10)(
    applyDiscountValueFunction)
  println(s"Total cost = $totalCostValue")

}
