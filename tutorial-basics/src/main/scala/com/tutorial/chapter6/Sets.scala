package com.tutorial.chapter6

import scala.collection.SortedSet
import scala.collection.immutable.{HashSet, TreeSet}

object Sets extends App {

  // SET
  val set1 = Set(1, 2, 3, 10, 23)

  // add elements
  val set2 = set1 + 1 + 1
  assert(set1 == set2)
  val set3 = set1 ++ Set(9, 999)

  // remove elements
  val set4 = set1 - 1
  val set5 = set3 -- set2

  // find diffs between sets
  val diffs = set3.diff(set2)
  diffs.foreach { println }
  val diffs2 = set3 &~ set1 // the same as above
  assert(diffs == diffs2)

  val empty = Set.empty[(Int, Int)]

  // HASH SET
  val hashSet = HashSet((1, 2), (2, 1), (3, 3))
  println(hashSet.contains((1, 2)))
  // syntax is the same
  // `+`  - add one element
  // `++` - add set to other set
  // `-`  - remove one element

  // intersection
  val hashSet2 = HashSet((1, 2), (4, 4))
  println(hashSet.intersect(hashSet2))
  println(hashSet & hashSet2)

  // TREE SET (provides ordering)
  object OwnOrdering extends Ordering[String] {
    override def compare(x: String, y: String): Int = {
      x.length - y.length
    }
  }
  val treeSet = TreeSet("Yes", "No", "I don't know", "Maybe")(OwnOrdering)
  println(treeSet.mkString(" | "))

  // SORTED SET
  val sortedSet = SortedSet("I", "like", "Scala")

}
