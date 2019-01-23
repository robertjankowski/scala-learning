package com.tutorial.chapter3

object ImplicitParameters extends App {

  def totalCost(donutType: String, quantity: Int)(
      implicit discount: Double): Double = {
    val totalCost = 2.5 * quantity * (1 - discount)
    totalCost
  }

  implicit val discount: Double = 0.1
  val donutType = "Vanilla donut"
  val quantity = 103
  println(totalCost(donutType, quantity))

  //

}
