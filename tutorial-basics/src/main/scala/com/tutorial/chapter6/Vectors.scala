package com.tutorial.chapter6

object Vectors extends App {

  val vector1 = Vector(2.0, 3.1, 2, 73)
  val find1 = vector1.find(p => p == 2.0).getOrElse("2.0 not found")
  println(find1)

  // append to end
  val vector2 = 3.123 +: vector1
  // in front of
  val vector3 = vector2 :+ 23.1

}
