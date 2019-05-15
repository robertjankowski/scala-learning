package com.tutorial.chapter6

import scala.collection.immutable.Stream.cons

object Streams extends App {

  // lazy compute
  val stream1 = Stream(1, 2, 3)
  val stream2: Stream[Int] = 1 #:: 2 #:: 3 #:: Stream.empty[Int]
  println(stream2)
  assert(stream1 == stream2)

  // create using Stream.cons
  val stream3 = cons(1, cons(2, cons(3, Stream.empty)))
  stream3.take(10).print // no error
  println()

  def infinityNumsStream(number: Int): Stream[Int] =
    Stream.cons(number, infinityNumsStream(number + 1))

  infinityNumsStream(1).take(20).print(", ")
  println()

  val stream4 = Stream.from(20)
  stream4.take(20).print(", ")
}
