package com.tutorial.chapter3

object PartialFunctionTut extends App {

  // Review of Pattern Matching
  val donut = "Vanilla"
  val tasteLevel = donut match {
    case "Vanilla" => "Very tasty"
    case _         => "Tasty"
  }
  println(s"$donut is $tasteLevel")

  // Partial Function [input type, output type]
  val isVeryTasty: PartialFunction[String, String] = {
    case "Vanilla" | "Strawberry" => "Very tasty"
  }

  val res = isVeryTasty(donut)
  println(res)
  val undefinedDonut = "undefined donut"
  // scala.MatchError
  // isVeryTasty(undefinedDonut)

  val isTasty: PartialFunction[String, String] = {
    case "Plain Donut" => "Tasty"
  }

  val unknownTaste: PartialFunction[String, String] = {
    case donut @ _ => s"Unknown donut: $donut"
  }

  // combine all functions in one
  val donutTaste = isVeryTasty orElse isTasty orElse unknownTaste
  println(s"$undefinedDonut is ${donutTaste(undefinedDonut)}")

  // testing
  val isGreaterThanZero: PartialFunction[Int, String] = {
    case x if x > 0 => "Greater then 0 !"
  }
  val isBelowZero: PartialFunction[Int, String] = {
    case x if x <= 0 => "Below zero !"
  }
  // compose functions together
  val checkSign = isGreaterThanZero orElse isBelowZero
  println(checkSign(1))
  println(checkSign(-12))

}
