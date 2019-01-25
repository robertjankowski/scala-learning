package com.tutorial.variables

object VariablesAndTypes extends App {

  val s: String = "Some string" // immutable variable
  // s = "sth" // error !

  // mutable variable
  var number: Float = 1000.3f
  number = 3


  // lazy initialization
  lazy val donut = "init lazy donut"

  // Scala Types
  /*
    - Int
    - Long
    - Short
    - Double
    - Float
    - String
    - Byte
    - Char
    - Unit (void in cpp)
   */

  var sth: String = _ // wildcard operator
  println("Sth:", sth) // null
  sth = "Sth"
  println(sth)



}
