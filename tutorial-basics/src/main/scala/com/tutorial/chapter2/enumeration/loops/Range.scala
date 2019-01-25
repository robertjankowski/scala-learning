package com.tutorial.chapter2.enumeration.loops

object Range extends App {

  // Alt + "="  - show type of variable
  var from1to5 = 1 to 5

  println(f"Created range from 1 to 5 = $from1to5")

  val from0to10by2 = 0 to 10 by 2

  val a = for {
    n <- from0to10by2
    if (n < 7)
  } yield n
  println(a)

  val alphabet = 'a' to 'z'
  println(alphabet.mkString) // without params -> default value

  // Range to List
  val listFromRange = (1 to 5).toList
  listFromRange.foreach { e =>
    println(2 * e)
  }
  def sqrt(x: Int) = x * x
  listFromRange.map(sqrt).foreach(println)

  // Range to Set
  val setFromRange = ('a' to 'd').toSet
  setFromRange.foreach(println)

  // Range to Seq
  val seqFromRange = ('a' to 'g' by 2).toSeq
  seqFromRange.foreach(e => print(f"${e}\t"))

  // Range to Array
  val arrFromRange = (1 until 10 by 3).toArray
  arrFromRange.foreach(println)

}
