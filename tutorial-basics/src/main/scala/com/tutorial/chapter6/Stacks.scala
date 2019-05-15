package com.tutorial.chapter6

import scala.collection.immutable.Stack

object Stacks extends App {

  val stack1 = Stack(10, 20, 2, 12)
  println(stack1.pop)

  // better use List instead of Stack (docs)
  val immList = List(10, 20, 2, 12)
  val stack2 = 12 :: immList
  println(stack2)

  // pop
  val stack3 = stack2.tail

  val stack4 = List.empty[Int]
}
