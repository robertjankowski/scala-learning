package com.tutorial.chapter5

trait DonutDatabase[A] {

  def addOrUpdate(donut: A): Long

  def query(position: Int): A

  def delete(donut: A): Boolean
}

class CassandraDonutStore[A] extends DonutDatabase[A] {

  var donuts = Vector.empty[A]

  override def addOrUpdate(donut: A): Long = {
    println(s"Add or update => donut: ${donut}")
    donuts = donuts :+ donut
    donuts.length
  }

  override def query(position: Int): A = {
    println(s"Get => position: ${position}")
    donuts(position)
  }

  override def delete(donut: A): Boolean = {
    println(s"Delete => donut: ${donut}")
    donuts = donuts.filter(d => d != donut)
    true // for testing
  }
}

trait DonutShoppingCartDao[A] {

  val donutDatabase: DonutDatabase[A]

  def add(donut: A): Long = donutDatabase.addOrUpdate(donut)

  def update(donut: A): Long = donutDatabase.addOrUpdate(donut)

  def search(position: Int): A = donutDatabase.query(position)

  def delete(donut: A): Boolean = donutDatabase.delete(donut)

}

trait DonutInventoryService[A] {

  val donutDatabase: DonutDatabase[A]

  def checkStockQuality(position: Int): A = {
    println("Check stock quality")
    donutDatabase.query(position)
  }

}

trait DonutShoppingCartServices[A]
    extends DonutShoppingCartDao[A]
    with DonutInventoryService[A] {
  override val donutDatabase: DonutDatabase[A] = new CassandraDonutStore[A]()
}

class DonutShoppingCartNew[A] extends DonutShoppingCartServices[A]

object DependencyInjection extends App {

  val donutService = new DonutShoppingCartNew[String]()

  donutService.add("First Donut")
  assert(donutService.search(0) == "First Donut")

}
