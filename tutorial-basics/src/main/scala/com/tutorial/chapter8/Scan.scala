package com.tutorial.chapter8

import java.lang.IndexOutOfBoundsException

object Scan extends App {
  val nums = (1 to 3).toList
  // running total
  val runTotal = nums.scan(0)(_ + _)
  println(runTotal.mkString(", "))

  // scanLeft + scanRight
  def subtract: (Int, Int) => Int = (a, b) => a - b

  val sLeft = nums.scanLeft(0)(subtract)
  val sRight = nums.scanRight(0)(subtract)
  println(s"Scan left: ${sLeft.mkString(", ")}")

  /**
    * 0
    * 0 - 1 => -1
    * -1 - 2 => -3
    * -3 - 3 => -6
    */
  println(s"Scan right: ${sRight.mkString(", ")}")

  /**
    * in reverse order
    * 0
    * 3 - 0 => 3
    * 2 - 3 => -1
    * 1 - (-1) => 2
    */
  // slice
  println(sRight.slice(0, sRight.size - 1).mkString(" | "))
  println(sLeft.slice(-1, 20)) // no exception IndexOutOfBoundsException

}
