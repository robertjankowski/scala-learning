package com.tutorial.chapter4

object FirstClass extends App {

  class Donut(name: String, productCode: Long) {
    def print = println(s"Donut name: $name, product code: $productCode")
  }

  val vanillaDonut = new Donut("Vanilla", 133333L)
  vanillaDonut.print

  // access the properties of class
  // error -> next tutorial how to use case class
  //  val name = vanillaDonut.name
}
