package com.tutorial.chapter4.inheritance

abstract class Donut(name: String) {
  def printName: Unit
}

class VanillaDonut(name: String) extends Donut(name) {
  override def printName: Unit = println(s"Vanilla donut ${name}")
}

object VanillaDonut {
  def apply(name: String): Donut = new VanillaDonut(name)
}

class GlazedDonut(name: String) extends Donut(name) {
  override def printName: Unit = println(s"Glazed Donut ${name}")
}

object GlazedDonut {
  def apply(name: String): Donut = new GlazedDonut(name)
}

object DonutMain extends App {
  val donut = VanillaDonut("Vanilla")
  donut.printName

  val glazedDonut = GlazedDonut("My donut 2")
  glazedDonut.printName
}
