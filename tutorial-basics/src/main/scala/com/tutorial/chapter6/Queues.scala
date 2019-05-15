package com.tutorial.chapter6

import scala.collection.immutable.Queue

object Queues extends App {

  val queue = Queue(1, 2, 19)
  println(queue.mkString(", "))

  // add element
  val queue1 = queue :+ 120
  println(s"Size of queue1 = ${queue1.size}")
  println(queue1.mkString(", "))

  // add queue to queue
  val queue2 = queue ++ Queue(666, 999)

  val next = queue2.dequeue
  print(s"NEXT: ${next._1}, ${next._2}")

  val empty = Queue.empty[Double]

}
