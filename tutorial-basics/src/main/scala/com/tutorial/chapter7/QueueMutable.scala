package com.tutorial.chapter7

import scala.collection.mutable

object QueueMutable extends App {

  val queue = mutable.Queue("One", "Two", "Three and half")
  queue.enqueue("Four")
  queue.foreach { println }

  queue.dequeue
  queue += "Five"
  println("After dequeue")
  queue.foreach { println }

  val empty = mutable.Queue.empty[(Int, Int)]

  // Priority Queue
  case class Donut(name: String, price: Double)
  def donutOrder(d: Donut) = d.price
  val priorityQueue = mutable.PriorityQueue(
    Donut("One", 1.5),
    Donut("Two", 2.1),
    Donut("Three", 0.99)
  )(Ordering.by(donutOrder))
  priorityQueue += Donut("Five", 1.99)
  priorityQueue.enqueue(Donut("Six", 9.99))

  // Empty
  val emptyP = mutable.PriorityQueue.empty[Int]
}
