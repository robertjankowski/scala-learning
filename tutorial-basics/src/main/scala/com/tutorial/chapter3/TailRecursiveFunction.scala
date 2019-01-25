package com.tutorial.chapter3

import scala.annotation.tailrec

object TailRecursiveFunction extends App {

  val arrayDonut: Array[String] =
    Array("Vanilla Donut", "Strawberry Donut", "Chocolate Donut")

  @tailrec
  def search(donutType: String,
             donuts: Array[String],
             donutIndex: Int): Option[Boolean] = {
    if (donuts.length == donutIndex) {
      None
    } else if (donuts(donutIndex) == donutType) {
      Some(true)
    } else {
      val nextIndex = donutIndex + 1
      search(donutType, donuts, nextIndex)
    }
  }

  val isChocolate =
    search("Chocolate Donut", arrayDonut, 1).getOrElse("No donut")
  val noWhiteChocolate = search("White Donut", arrayDonut, 3)
  println(s"Is chocolate? == $isChocolate")
  println(s"Is no white chocolate ? == $noWhiteChocolate")
}
