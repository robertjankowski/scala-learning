package com.tutorial.chapter8

object PartitionReduce extends App {
  val someRandomList = List(1.0, "High five", -23, Array.empty[Boolean])

  val res = someRandomList.partition {
    case e: Double =>
      println(s"Val: $e")
      true
    case _ => false // default param
  }
  println(res._1.mkString(", "))
  println(res._2.mkString(", "))

  // reduce
  val l = Seq("One", "Two", "Three")
  println(l.reduce(_ + _))
  println(l.map(_.toUpperCase).reduce((s1, s2) => s1 + ", " + s2 + "\t"))

  val concatString: (String, String) => String = (left, right) => {
    left + " | " + right
  }
  println(l.reduce(concatString))

  // reduce left and right
  val a = List(3.03, 323.0, 23.0)
  println(a.reduceLeft(_ - _)) // with tail recursion
  println(a.reduceRight(_ - _))

}
