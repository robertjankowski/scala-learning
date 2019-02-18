package tourOfScala

import scala.annotation.tailrec

object Annotations extends App {

  @deprecated("Deprecation message", "release # which deprecated me")
  def hello = "hello"

  println(hello)

  def factorial(x: Int): Int = {
    @tailrec
    def factorialHelper(x: Int, accumulator: Int): Int = {
      if (x == 1)
        accumulator
      else
        factorialHelper(x - 1, accumulator * x)
    }
    factorialHelper(x, 1)
  }
  println(factorial(10))

  // default parameters values
  def log(message: String, level: String = "INFO") =
    println(s"$level: $message")
  log("System starting")
  log("User not found", "WARN")

  class Point(val x: Double = 0, val y: Double = 0)
  val p = new Point()
  println(p.x)

  // named arguments
  def printName(first: String, last: String) = println(first + " " + last)
  printName(first = "Rob", last = "Jan")

  // package objects
  showFruit(fruit)

}
