package com.tutorial.chapter8

object FilterFindFlatmapFlatten extends App {

  val seq = Seq("One", "Two", "Three", "Four", "Five")
  seq.filter(_.length > 3).foreach(println)

  // filterNot
  val ex1 = seq.filterNot(_.contains("One")).mkString(",")
  println(ex1)

  // find
  val two = seq.find(_.charAt(0) == "T").getOrElse("No two in seq")
  println(two)
  val notSix = seq.find(_.charAt(0) == "S").getOrElse("No six in seq")
  println(notSix)

  // FlatMap
  val seq2 = Seq("Two", "Seven", "Eight")
  val seqList = List(seq, seq2)
  println(seqList.flatMap(seq => seq)) // flatten list
  seqList
    .flatMap { num =>
      num.map(_.toUpperCase)
    }
    .foreach { println }

  // Flatten
  val afterFlatten = seqList.flatten
  println(afterFlatten.mkString(","))

}
