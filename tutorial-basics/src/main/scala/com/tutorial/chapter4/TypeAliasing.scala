package com.tutorial.chapter4

import CaseClasses.Donut

object TypeAliasing extends App {
  val donut = Donut("Name", 100)

  type CartItem[Donut, Int] = (Donut, Int) // Tuple2

  val cartItem = new CartItem(donut, 10)
  println(cartItem.getClass)
  println(s"First item = ${cartItem._1}, second item = ${cartItem._2}")

  def calculateTotalCost(cartItems: Seq[CartItem[Donut, Int]]): Double = {
    cartItems.foreach { item =>
      println(s"First item: ${item._1}, second item: ${item._2}")
    }
    10
  }

  calculateTotalCost(Seq.fill(3)(cartItem))

  // example with matrix
  type Row = List[Int]

  def Row(xs: Int*) = List(xs: _*)

  type Matrix = List[Row]

  def Matrix(xs: Row*) = List(xs: _*)

  val m = Matrix(
    Row(1, 2, 3),
    Row(1, 3, 4)
  )
  m.foreach { r =>
    r.foreach(print(_))
  }

}
