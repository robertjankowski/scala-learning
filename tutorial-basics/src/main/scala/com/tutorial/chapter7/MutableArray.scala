package com.tutorial.chapter7

import scala.collection.mutable.{ArrayStack, ArrayBuffer}

object MutableArray extends App {

  // ARRAY
  val array = Array(1, 2, 3)
  val array2 = new Array[String](2)
  array2(0) = "Bobby"
  array2(1) = "Timothy"
  println(array2.mkString(" | "))

  // 2D array
  val rows = 10
  val cols = 5
  val array3 = Array.ofDim[Int](rows, cols)
  for (i <- 1 until array3.size) {
    for (j <- 1 until array3(i).size) {
      array3(i)(j) = i * j
    }
  }
  array3.deep.toList.foreach(e => print(s"${e}, "))
  println()

  val array4 = Array.tabulate(5)(_ + 1)
  println(array4.toList)
  val array5 = Array.tabulate(3, 3)((row, cols) => row + cols)
  println(array5.deep.toList)

  val array6 = (1 to 10).toArray[Int]
  val cloneArray = array6.clone()
  val reversedArray = cloneArray.reverse
  println(reversedArray.toList)
  println(s"Same elements ${array6 sameElements cloneArray}")

  //
  // ARRAY BUFFER
  val arrayBuffer = ArrayBuffer("I", "Know", "What", "I", "am", "doing")
  arrayBuffer += "!" // fixed size
  arrayBuffer ++= List("And", "It", "is", "great")
  println(arrayBuffer.mkString(" "))

  val emptyArrayBuffer = ArrayBuffer.empty[(Int, String)]

  //
  // ARRAY STACK - LIFO
  // push and pop methods
  val arrayStack = ArrayStack(2.0, 2.1, 2.2)
  println(arrayStack.pop)
  arrayStack.push(10.1)
  println(arrayStack.size)
}
