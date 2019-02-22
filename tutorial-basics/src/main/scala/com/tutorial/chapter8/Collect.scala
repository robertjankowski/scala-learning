package com.tutorial.chapter8

object Collect extends App {

  val things = Seq(1.0, 3, "thing", "many", List(1))

  val onlyNumbers = things.collect { case num @ (Int | Double) => num }
  onlyNumbers.foreach { println }

}
