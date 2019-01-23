package com.tutorial.chapter3

import scala.util.control.TailCalls._

object TrampolineTailRecursiveFunction extends App {
  def verySweetDonut(donuts: List[String]): TailRec[Boolean] = {
    println(s"VerySweetDonut function: donut list = $donuts")
    if (donuts.isEmpty) {
      println("VerySweetDonut function: donut list empty, false")
      done(false)
    } else {
      if (Set(donuts.head).subsetOf(
            Set("Vanilla Donut", "Strawberry Donut", "Chocolate Donut"))) {
        println(
          s"VerySweetDonut function: found donut list's head = ${donuts.head} to be VERY sweet, true")
        done(true)
      } else {
        println(
          s"VerySweetDonut function: fount donut list's head = ${donuts.head} to NOT be VERY sweet, go to notSweetDonut function")
        tailcall(notSweetDonut(donuts))
      }
    }
  }

  def notSweetDonut(donutList: List[String]): TailRec[Boolean] = {
    println(s"notSweetDonut function: with donut list = $donutList")
    if (donutList.isEmpty) {
      println("notSweetDonut function: donut list isEmpty, returning false")
      done(false)
    } else {
      println(
        s"notSweetDonut function: donut list's head = ${donutList.head} is NOT sweet," +
          "forwarding donut list's tail to verySweetDonut function")
      tailcall(verySweetDonut(donutList.tail)) // `tail` => Returns this list without its first element.
    }
  }

  val donutList: List[String] =
    List("Plain Donut", "Strawberry Donut", "Plain Donut", "Glazed Donut")
  val foundVerySweetDonut = tailcall(verySweetDonut(donutList)).result
  println(s"Found very sweet donut = $foundVerySweetDonut")

}
