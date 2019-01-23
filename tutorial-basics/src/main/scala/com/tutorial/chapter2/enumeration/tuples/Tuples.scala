package com.tutorial.chapter2.enumeration.tuples

object Tuples extends App {
  var point = Tuple2(1, 2)
  println(point.swap)

  // 2, 3 or more elements in Tuple is possible
  val glazedDonut = Tuple3("Glazed Donut", "Very Tasty", 2.50)
  val strawberryDonut = Tuple3("Strawberry Donut", "Very Tasty", 2.50)
  val plainDonut = Tuple3("Plain Donut", "Tasty", 2)

  var donutList = List(glazedDonut, strawberryDonut, plainDonut)
  var priceOfPlainDonut = donutList.foreach {
    case ("Plain Donut", taste, price) =>
      println(s"Donut type = Plain Donut, price = $price")
    case d if d._1 == "Glazed Donut" =>
      println(s"Donut type = ${d._1}, price = ${d._3}")
    case _ => None
  }

}
