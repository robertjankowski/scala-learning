package com.tutorial.chapter4

object ExtensionMethods extends App {

  // case class
  case class Donut(name: String,
                   price: Double,
                   productCode: Option[Long] = None) {}

  val vanillaDonut = Donut("Vanilla", 10.0)

  // define an implicit class to argument or extend the Donut object with a uuid field
  object DonutImplicit {
    // wraps Donut class and extends class methods
    implicit class AugmentedDonut(donut: Donut) {
      def uuid: String = s"${donut.name} - ${donut.price}"
      val test: Double = 20
    }
  }

  import DonutImplicit._
  println(s"Vanilla donut uuid = ${vanillaDonut.uuid}")
  println(s"TEST: ${vanillaDonut.test}")

  import scala.math.sqrt
  // test
  object Helper {
    implicit class NewSqrt(d: Double) {
      val doubleSqrt: Double = sqrt(sqrt(d))
    }
  }
  println(s"${Helper.NewSqrt(10).doubleSqrt}")

}
