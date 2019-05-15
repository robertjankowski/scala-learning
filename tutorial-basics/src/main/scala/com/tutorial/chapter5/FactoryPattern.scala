package com.tutorial.chapter5

import com.tutorial.chapter5.Cakes._

object Cakes {

  trait Cake {
    def name: String
  }

  class UnknownCake extends Cake {
    override def name: String = "Unknown cake but also tasty!"
  }

  class Cupcake extends Cake {
    override def name: String = "Cupcake"
  }

  class DonutCake extends Cake {
    override def name: String = "Donut"
  }

}

object CakesFactory {

  def apply(cake: String): Cake = cake match {
    case "cupcake" => new Cupcake
    case "donut"   => new DonutCake
    case _         => new UnknownCake
  }
}

object FactoryPattern extends App {

  println(CakesFactory("cupcake").name)
  println(CakesFactory("donut").name)
  println(CakesFactory("unknown").name)

}
