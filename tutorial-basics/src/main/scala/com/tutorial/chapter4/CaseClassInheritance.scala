package com.tutorial.chapter4

abstract class Donut2(name: String) {
  def printName: Unit
}

case class VanillaDonut(name: String) extends Donut2(name) {
  override def printName: Unit = println(s"Vanilla = ${name}")
}

case class GlazedDonut(name: String) extends Donut2(name) {
  override def printName: Unit = println(s"Glazed = ${name}")
}

class ShoppingCart[D <: Donut2](donuts: Seq[D]) {
  // only Donut type or below in inheritance tree
  def printCartItem: Unit = donuts.foreach {
    _.printName
  }
}

class ShoppingCart2[+D <: Donut2](donuts: Seq[D]) {
  // covariance
  def printCartItem: Unit = donuts.foreach {
    _.printName
  }
}

class ShoppingCart3[-D <: Donut2](donuts: Seq[D]) {
  // contra-variance
  def printCartItem: Unit = donuts.foreach {
    _.printName
  }
}

object CaseClassInheritance extends App {

  val glazedDonut = GlazedDonut("My sweet donut")
  val vanillaDonut = VanillaDonut("My second sweet donut")

  val cart = new ShoppingCart(Seq(vanillaDonut, glazedDonut))
  cart.printCartItem

  val cart2 = new ShoppingCart2[Donut2](Seq(vanillaDonut, glazedDonut))
  cart2.printCartItem

  val cart3: ShoppingCart3[VanillaDonut] = new ShoppingCart3(
    Seq(vanillaDonut, glazedDonut))
  cart3.printCartItem
}
