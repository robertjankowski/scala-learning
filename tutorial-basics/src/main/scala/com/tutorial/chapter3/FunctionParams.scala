package com.tutorial.chapter3

object FunctionParams extends App {

  def calculateDonutCost(donutName: String, quantity: Int) = {
    println(donutName, quantity)
    2.8 * quantity
  }

  val donutName = "My donut"
  val quantity = 230
  println(calculateDonutCost(donutName, quantity))

  // default value
  def calculateDonutCostDefault(donutName: String,
                                quantity: Int,
                                coupon: String = "No coupon"): Double = {
    println(donutName, quantity, coupon)
    if (coupon != "No coupon") {
      return quantity * 1.0
    } else {
      return quantity * 2.0
    }
  }
  println(calculateDonutCostDefault(donutName, quantity, "I have coupon"))

}
