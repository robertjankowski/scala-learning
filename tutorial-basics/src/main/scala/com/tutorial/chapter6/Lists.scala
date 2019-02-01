package com.tutorial.chapter6

object Lists extends App {

  val list = List("Rob", "Bob")
  //  println(list(1))

  // append elements to list
  val list1 = list :+ "New name"
  //  list1.foreach { println }

  val list2 = "First elem" +: list1

  // list of lists
  val twoLists = list1 :: list2
  twoLists.foreach {
    println
  }

  // add list to list and return list
  val addLists = list1 ::: list2

  // init empty list
  val empty = List.empty[String]

}
