package com.tutorial.chapter3

object ImplicitFunctions extends App {

  object DonutConverstions {
    implicit def stringToDonut(s: String) = new DonutString(s)
  }

  import DonutConverstions._
  val isFav = "Chocolate".isFavDonut
  println(s"isFav? = $isFav")

  // this is
  implicit def toDouble(d: Double) = d.toInt
  val x: Int = 4.23

  // the same as
  def toDoubleNotImplicit(d: Double) = d.toInt
  val y: Int = toDoubleNotImplicit(3.42)

}
