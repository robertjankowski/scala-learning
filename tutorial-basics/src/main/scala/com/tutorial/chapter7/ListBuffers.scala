package com.tutorial.chapter7

import scala.collection.mutable.ListBuffer

object ListBuffers extends App {

  val listBuffer = ListBuffer("One", "Two", "Three")
  listBuffer.foreach { println }

  // add element
  listBuffer += "Four"
  listBuffer ++= List("Five", "Six")
  // remove element
  listBuffer --= List("Two", "Three")

  // empty
  val listEmpty = ListBuffer.empty[Int]

}
