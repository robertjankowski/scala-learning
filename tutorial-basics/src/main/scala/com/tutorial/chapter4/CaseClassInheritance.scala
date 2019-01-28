package com.tutorial.chapter4

abstract class Donut2(name: String) {
  def printName: Unit
}

case class GlazedDonut(name: String) extends Donut2(name) {
  override def printName: Unit = println(name)
}

object CaseClassInheritance extends App {

  val glazedDonut = GlazedDonut("My sweet donut")
  glazedDonut.printName
}
