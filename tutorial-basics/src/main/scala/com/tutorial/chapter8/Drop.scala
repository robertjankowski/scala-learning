package com.tutorial.chapter8

object Drop extends App {

  val seq = Seq("One", "Two", "Three", "Four", "Five")
  println(s"Drop the first element in seq ${seq.drop(1)}")

  println(s"Drop too many elements ${seq.drop(100)}") // -> empty list

  // DropWhile - drops longest prefix of elements that satisfy a predicate.
  val oInString = seq.dropWhile(_.charAt(0) == 'O')
  println(oInString.mkString(","))

  val dropTooShort: String => Boolean = (name) => name.contains("F")
  val exceptShort = seq.dropWhile(dropTooShort)
  println(exceptShort.mkString(","))

  // Exists
  val isTwo = seq.exists(_.contains("Two"))
  println(s"Two ${if (isTwo) "yes" else "no"}")
}
