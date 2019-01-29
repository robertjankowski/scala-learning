package com.tutorial.chapter5

trait DonutShoppingCart {

  def add(donutName: String): Long

  def update(donutName: String, newName: String): Boolean

  def search(donutName: String): String

  def delete(donutName: String): Boolean

  def printDonuts(): Unit
}

class DonutShopping extends DonutShoppingCart {

  var donuts = List.empty[String]

  override def add(donutName: String): Long = {
    println(s"DonutShoppingCart => add method => donutName: ${donutName}")
    // very weird way for Scala, just for testing !
    donuts = donutName :: donuts
    donuts.size
  }

  override def update(donutName: String, newName: String): Boolean = {
    val toUpdate = donuts.filter(donut => donut == donutName).nonEmpty
    donuts =
      donuts.map(donut => if (donut.equals(donutName)) newName else donut)
    toUpdate
  }

  override def search(donutName: String): String = {
    donuts.filter(donut => donut == donutName).head
  }

  override def delete(donutName: String): Boolean = {
    donuts = donuts.filter(donuts => donuts != donutName)
    donuts.nonEmpty
  }

  override def printDonuts(): Unit = {
    donuts.foreach {
      println
    }
  }

}

object FirstTrait extends App {

  val donutShopping = new DonutShopping

  for (i <- 1 to 3) {
    donutShopping.add(s"My donut ${i}")
  }

  donutShopping.printDonuts

  println("After  operations")
  donutShopping.delete("My donut 1")
  donutShopping.update("My donut 2", "My updated donut 2")

  donutShopping.printDonuts

}
