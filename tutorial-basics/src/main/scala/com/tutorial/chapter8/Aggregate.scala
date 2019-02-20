package com.tutorial.chapter8

object Aggregate extends App {

  val bucket = Set("One", "Two", "Three")

  val lengthAcc: (Int, String) => Int = (acc, name) => acc + name.length

  val res = bucket.aggregate(0)(lengthAcc, _ + _)
  println(s"Length: $res")

  case class Money(name: String, value: Double, quantity: Int)
  val m = Set(Money("Ten", 10, 1), Money("Two", 2, 10), Money("Four", 4, 2))

  val amount: Money => (String, Double) = m => (m.name, m.value * m.quantity)
  val money = m.map(amount)
  money.foreach { println }
  val total: (Double, Money) => Double = (acc, e) => acc + e.quantity * e.value
  val res1 = m.aggregate(0.0)((a: Double, m: Money) => total(a, m), _ + _)
  println(s"Sum of all money: $res1")

}
