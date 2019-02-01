package com.tutorial.chapter6

import scala.collection.immutable.ListSet

object ListSets extends App {

  val listSet = ListSet[String]("Rob", "Tom", "Jerry")
  println(s"$listSet")

  // check if element is in listSet
  println(s"${listSet("Mark")}")
  println(s"${listSet("Tom")}")

  // add element to ListSet
  val newListSet = listSet + "New name"
  println(s"$newListSet\n")

  // add ListSet to ListSet
  val listSet2 = listSet ++ ListSet("Tommy")
  println(s"$listSet2\n")

  // remove element
  val listSet3 = listSet2 - "Tommy"
  assert(listSet == listSet3)
  println(s"Head of listSet3 = ${listSet3.head}")

  // empty ListSet
  val empty = ListSet.empty[String]
}
