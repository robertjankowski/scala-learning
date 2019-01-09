package com.tutorial.chapter3

object FunctionParameters extends App {
  def calcDonutCost(donutName: String,
                    quantity: Int,
                    couponCode: Option[String] = None): Double = {
    couponCode match {
      case Some(coupon) =>
        val discount = 0.1
        val totalCost = 2.5 * quantity * (1 - discount)
        totalCost

      case None =>
        2.5 * quantity
    }
  }

  val donutName = "Donut"
  val quantity = 399
  val cost = calcDonutCost(donutName, quantity)
  println(s"Cost = ${cost}")
  val cost1 = calcDonutCost(donutName, quantity, Some("Code"))
  println(s"Cost with coupon = $cost1")

  // map function to extract a valid Option value
  val favDonut = Some("Vanilla donut")
  favDonut map println

}
