package com.tutorial.chapter2.enumeration.option

object OptionSomeNone extends App {

  val optionDonut: Option[String] = Some("Donut !!!")
  println(s"Donut = ${optionDonut.get}, optionDonut = ${optionDonut}")

  // None example
  val noneDonut: Option[String] = None
  println(s"Donut = ${noneDonut.getOrElse("None donut")}")

  // pattern matching none
  noneDonut match {
    case Some(value) => println(s"${value}")
    case None        => println("None donut ! ! !")
  }

}
