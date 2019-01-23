package com.tutorial.chapter3

object ComposeFunctions extends App {
  val totalCost = 100

  val applyDiscount = (amount: Double) => {
    val discount = 10
    amount - discount
  }
  val applyTaxValFunction = (amount: Double) => {
    println("Apply tax function")
    val tax = 1 // fetch tax from database
    amount + tax
  }

  val composeFunction = applyDiscount compose applyTaxValFunction
  println(s"Last price = ${composeFunction(totalCost)}")

  // Ordering using andThen: f(x) andThen g(x) = g(f(x))
  // Ordering using compose: f(x) compose g(x) = f(g(x))
}
