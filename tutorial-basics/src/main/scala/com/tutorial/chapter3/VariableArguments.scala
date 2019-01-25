package com.tutorial.chapter3

object VariableArguments extends App {
  // `varargs` as in R three dots ... => zero or more params in functions
  def printReport(names: String*) = {
    println(s"""
         |Donut Report = ${names.mkString(" - ")}
       """.stripMargin)
  }

  printReport("Vanilla", "Chocolate")
  printReport()

  // pass a list to function which takes zero or more strings
  val listDonut: List[String] = List("Vanilla", "Chocolate", "Jam")
  // type ascription
  printReport(listDonut: _*)

}
