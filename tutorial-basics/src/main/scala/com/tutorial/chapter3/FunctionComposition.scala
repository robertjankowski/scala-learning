package com.tutorial.chapter3

object FunctionComposition extends App {
  // val function and `andThen` function
  val totalCost: Double = 10

  val applyDiscount = (amount: Double) => {
    println("Apply discount function")
    val discount = 3
    amount - discount
  }

  println(s"Total cost = ${applyDiscount(totalCost)}")

  // apply tax to totalCost
  val applyTaxes = (amount: Double) => {
    println("Apply tex function")
    val tax = 1
    amount + tax
  }

  // val function inherits andThen function
  val cost = applyDiscount.andThen(applyTaxes)(totalCost)
  println(s"Total cost after discount and taxes = $cost")

  // combine functions into one function :)
  val costCompose = applyTaxes.compose(applyDiscount)
  println(costCompose(totalCost))

  def applyTax(amount: Double): Double = {
    val tax = 2
    amount - tax
  }

  println()
  // difference between val and def
  // https://alvinalexander.com/scala/fp-book-diffs-val-def-scala-functions
  val f: (Any) => String = {
    case _: Int    => "Int"
    case _: Double => "Double"
    case _         => "Other"
  }
  println(f(1))
  println(f("1"))
  println(f(1.0))

  // same results !!
  val sum = (a: Int, b: Int) => a + b
  val sum1 = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = {
      a + b
    }
  }

  // def function => method !! need to be defined within a class or object
  def add(a: Int, b: Int): Int = {
    a + b
  }

  println(s"${sum(1, 1) == sum1(1, 1) == add(1, 1)}") // true

  // using parameterized (generic) types
  def firstChar[A](a: A) = a.toString.charAt(0)

  println(firstChar("12"))
  println(firstChar(12))
  // not compiled
  // val firstCharVal[A] = (a: A) => a.toString.charAt(0)

  // coerce a parametrized method into a function
  def lengthOfThing[A] = (a: A) => a.toString.length

  val lenInt = lengthOfThing[Int]
  println(s"Len of 1000 = ${lenInt(1000)}")

}
