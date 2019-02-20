package com.tutorial.chapter8

object Drop extends App {

  val seq = Seq("One", "Two", "Three", "Four", "Five")
  println(s"Drop the first element in seq ${seq.drop(1)}")

  println(s"Drop too many elements ${seq.drop(100)}") // -> empty list
}
