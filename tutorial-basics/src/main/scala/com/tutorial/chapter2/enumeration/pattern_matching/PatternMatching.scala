package com.tutorial.chapter2.enumeration.pattern_matching

object PatternMatching extends App {
  // similar to case statement

  val donut = "Donut"
  donut match {
    case "Donut"     => println("Yes")
    case "Not donut" => println("No")
  }
  val s: List[String] = List[String]("1", "2", "3", "4")
  val toIntS = s.map(_.toInt).sortBy(-_)
  println(toIntS)

  val donut1 = "Not donut"
  val tasteLevel = donut1 match {
    case "Not donut" =>
      (x: Int) =>
        x * x
    case "Donut" => "Yes"
  }

  val tasteLvl = donut match {
    case "Sweet donut" => println("OOO sweet donut boi")
    case "Donut"       => "Tasty"
    case _             => "Tasty"
  }
  println(tasteLevel.toString)

  val donutList = List[String]("Sweet", "Sour", "Bitter")
  def specialSum(num: String): Int = {
    num match {
      case "Sweet" => 10
      case "Sour"  => 20
      case _       => 100
    }
  }
  val res = donutList.map(specialSum)
  println(res)

}
