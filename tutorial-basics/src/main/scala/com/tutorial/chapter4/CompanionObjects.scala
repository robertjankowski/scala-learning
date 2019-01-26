package com.tutorial.chapter4

import scala.util.Random
import scala.math.ceil

object CompanionObjects extends App {

  // create object without having use of the `new` keyword

  // companion object
  object Donut {
    val r = new Random(10)

    def apply(name: String, productCode: Long): Donut = {
      val newName: String = name match {
        case "Vanilla"   => "Tasty Donut"
        case "Chocolate" => "Very tasty donut"
        case _           => "Unknown taste level"
      }
      val newProductCode: Long = ceil(productCode * r.nextDouble()).toLong
      new Donut(newName, newProductCode)
    }
  }
  val myDonut = Donut("My Donut", 10334L)

}
