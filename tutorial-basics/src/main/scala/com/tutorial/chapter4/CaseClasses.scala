package com.tutorial.chapter4

object CaseClasses extends App {

  // case classes will create automatically `apply()`, `toString` and `hashCode` and `equals`

  case class Donut(name: String,
                   price: Double,
                   productCode: Option[Long] = None) {}

  val donut = Donut("Vanilla", 9.99, Some(10L))
  val donut1 = Donut("Vanilla", 10)
  println(donut)
  println(donut == donut1)

  // access fields (immutable by default)
  val name = donut.name
  val price = donut1.price

  val shoppingCart: Map[Donut, Int] = Map(donut1 -> 10, donut -> 4)
  println(shoppingCart)
  // normal int -> java.util.NoSuchElementException: key not found
  // val test = shoppingCart(Donut("V", 1))
  // by default map return Option[Int]
  val getInt = shoppingCart.get(donut).get

  // copy method
  val copyDonut = donut.copy("NEW name")
  println(copyDonut)

}
