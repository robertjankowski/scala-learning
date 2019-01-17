package com.tutorial.chapter3

object NestedFunctions extends App {

  def checkDonutAvailability(donutName: String): Boolean = {
    // retrieve donut list that is currently in stock
    val listDonutsFromStock: List[String] =
      List("Vanilla Donut", "Strawberry Donut", "Plain Donut", "Glazed Donut")
    val iDonutInStock = (donutName.nonEmpty && donutName.length > 0) && (listDonutsFromStock contains donutName)
    iDonutInStock
  }

  println(
    s"Calling function with Vanilla Donut: ${if (checkDonutAvailability("Vanilla Donut")) "yes"
    else "no"}")

  // define a nested function
  def checkDonutAvailabilityWithNestedFunction(donutName: String): Boolean = {
    // retrieve donut list that is currently in stock
    val listDonutsFromStock = List[String]("Vanilla Donut",
                                           "Strawberry Donut",
                                           "Plain Donut",
                                           "Glazed Donut")
    // validate the donutName parameter by some business logic
    val validate = (name: String) => {
      name.nonEmpty && name.length > 0
    }
    // first run validate and then check if we have a matching donut from our list
    validate(donutName) && (listDonutsFromStock contains donutName)
  }

  println(
    s"Calling nested function with Vanilla Donut: " +
      s"${if (checkDonutAvailabilityWithNestedFunction("Vanilla Donut")) "yes"
      else "no"}")

}
