package com.tutorial.chapter4

object MethodAsAFactory extends App {

  class Donut(name: String, productCode: Option[Long] = None) {
    def print =
      println(
        s"Name: $name, productCode: ${productCode.getOrElse("None code")}")
  }

  // inheritance
  class VanillaDonut(name: String) extends Donut(name)

  class GlazedDonut(name: String) extends Donut(name)

  // declare apply method of companion object as a factory
  object Donut {
    def apply(name: String): Donut = {
      name match {
        case "Glazed Donut"  => new GlazedDonut(name)
        case "Vanilla Donut" => new VanillaDonut(name)
        case _               => new Donut(name)
      }
    }
  }

  //
  val glazedDonut = Donut("Glazed Donut")
  glazedDonut.print
  val vanillaDonut = Donut("Vanilla Donut")
  vanillaDonut.print

  val classDonut = glazedDonut.getClass
  println(classDonut)

}
