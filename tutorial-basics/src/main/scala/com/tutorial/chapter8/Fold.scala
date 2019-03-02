package com.tutorial.chapter8

object Fold extends App {

  val seq = Seq(1.0, 2.0, 2.5, 4.01)
  val sum1 = seq.sum
  val sum2 = seq.fold(0.0)(_ + _)
  println(sum1 == sum2)

  val seq2 = Seq("One", "Two", "Three")
  var i = 1
  val oneString = seq2.fold("")((acc, s) => {
    i += 1
    acc + s + " " + i
  })
  println(oneString)

  // foldLeft
  println(seq.foldLeft(0.0) { (acc, value) =>
    println("foldLeft val1 " + acc + " val2 " + value)
    acc - value
  })

  // foldRight
  println(seq.foldRight(0.0) { (acc, value) =>
    println("foldLeft val1 " + acc + " val2 " + value)
    acc - value
  })
}
