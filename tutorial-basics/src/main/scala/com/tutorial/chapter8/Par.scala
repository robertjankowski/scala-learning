package com.tutorial.chapter8

import scala.collection.parallel.ParSeq
import scala.collection.parallel.mutable.ParArray

object Par extends App {

  val l = List(1.0, 2.0, 232.02, 23.23, 0.023)

  val parL: ParSeq[Double] = l.par // copy objects
  parL.map(x => x * x * x).foreach { println } // not in particular order

  val parArray = ParArray.tabulate(1000)(x => Math.sqrt(x))
  println(parArray.sum)

}
