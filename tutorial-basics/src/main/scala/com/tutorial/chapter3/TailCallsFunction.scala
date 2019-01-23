package com.tutorial.chapter3

import scala.util.control.TailCalls._

object TailCallsFunction extends App {
  val donuts: Array[String] =
    Array("Vanilla Donut", "Strawberry Donut", "Chocolate Donut")

  def tailSearch(donutName: String,
                 donuts: Array[String],
                 index: Int = 0): TailRec[Option[Boolean]] = {
    if (donuts.length == index) {
      done(None)
    } else if (donuts(index) == donutName) {
      done(Some(true))
    } else {
      val nextIndex = index + 1
      tailcall(tailSearch(donutName, donuts, nextIndex))
    }
  }

  val isChocolateDonut = tailSearch("Chocolate Donut", donuts).result.get
  println(isChocolateDonut)
}
