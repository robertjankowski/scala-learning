package com.exercises

object last_index extends App {
  // EX 1
  def last(l: List[Int]): Int = {
    return l.last
  }

  val a = last(List(1, 2, 3, 4))
  println(s"Last element = ${a}") // 4

  // EX 2
  def peneultimate(l: List[Int]): Int = {
    return l(l.length - 2)
  }

  val b = peneultimate(List(2, 5, 6, 9)) // 6
  println(s"Last but one = ${b}")

  // EX 3
  def nth(n: Int, l: List[Int]): Int = {
    return l(n)
  }

  val k: Int = 2
  val c = nth(k, List(2, 5, 99, 9))
  // iterate from 0 index
  println(s"${k}-th element of list = ${c}") // 99

  // EX 4
  def length(l: List[Int]): Int = {
    return l.size
  }

  println("Size = ", length(List(2, 4, 56))) // 3

  // EX 5
  def reverse(l: List[Int]): List[Int] = {
    return l.reverse
  }

  val d: List[Int] = reverse(List(9, 8, 1))
  println(d)

  // EX 6
  def isPalidrome(l: List[Int]): Boolean = {
    return l == l.reverse
  }

  println(s"Is palidrome? ${d} => ${isPalidrome(d)} ")

  // EX 7
  def flatten(l: List[Any]): List[Any] = l flatten {
    case i: List[_] => flatten(i)
    case e          => List(e)
  }

  val l: List[Any] = List(1, List(2, 3, List(4, 5)))
  println(s"Flatten list = ${flatten(l)}")

  // TODO:
  // EX 8
}
