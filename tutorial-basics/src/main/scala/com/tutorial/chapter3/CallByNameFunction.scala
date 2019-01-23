package com.tutorial.chapter3

import scala.util.Random

object CallByNameFunction extends App {
  val listOrder = List(("Glazed Donut", 5, 2.50), ("Vanilla Donut", 10, 3.50))

  // loop over all elements in list
  def placeOrder(orders: List[(String, Int, Double)])(
      exchangeRate: Double): Double = {
    var totalCost = 0.0
    orders.foreach { order =>
      val costOfItem = order._2 * order._3 * exchangeRate
      println(s"Cost of ${order._1} = $costOfItem")
      totalCost += costOfItem
    }
    totalCost
  }

  // call-by-name function
  def placeOrderWithByNameParam(orders: List[(String, Int, Double)])(
      exchangeRate: => Double): Double = {
    var totalCost: Double = 0.0
    orders.foreach { order =>
      val costOfItem = order._2 * order._3 * exchangeRate
      println(s"Cost of ${order._2} ${order._1} = ${costOfItem}")
      totalCost += costOfItem
    }
    totalCost
  }

  // convert USD to GBP
  val randomExchangeRate = new Random(10)

  def usdToGbp: Double = {
    val rate = randomExchangeRate.nextDouble()
    println("Fetching USD to GBP exchange rate = %.4f".format(rate))
    rate
  }

  placeOrder(listOrder)(usdToGbp)
  placeOrderWithByNameParam(listOrder)(usdToGbp)

}
