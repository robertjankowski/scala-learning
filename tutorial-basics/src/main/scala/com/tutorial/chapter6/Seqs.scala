package com.tutorial.chapter6

object Seqs extends App {

  val seq1 = Seq("Rob", "Mark", "Tony", "Lucy", "Robbin")

  val first = seq1(0)
  val newSeq = seq1 :+ "Martin"
  assert(newSeq.size - 1 == seq1.size)

  val empty = Seq.empty[Int]

  val fillSeq = Seq.fill(10)(2)
  fillSeq.foreach { println }
}
