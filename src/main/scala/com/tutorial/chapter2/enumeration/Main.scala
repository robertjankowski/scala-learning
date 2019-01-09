package com.tutorial.chapter2.enumeration

object Main extends App {
  val donut = Donut.Chocolate
  println(donut)

  println(s"Id of chocolate donut = ${donut.id}")

  // different type of implementation
  Donut.values foreach { e =>
    println(e)
  }
  Donut.values foreach println
  Donut.values.foreach(println)
}
