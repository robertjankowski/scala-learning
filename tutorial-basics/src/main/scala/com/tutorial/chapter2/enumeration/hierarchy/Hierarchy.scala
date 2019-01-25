package com.tutorial.chapter2.enumeration.hierarchy

object Hierarchy extends App {
  val favDonut: Any = 3.4f
  val typeOfDonut = favDonut.getClass.getCanonicalName
  println(s"Favdonut = ${typeOfDonut}")

}
