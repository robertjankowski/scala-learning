package com.tutorial.chapter7

import scala.collection.mutable
import scala.collection.mutable._

object MutableSet extends App {

  val set = Set(10, 20, 30, 10, 1, 20, 99)
  set.foreach { println }

  set += 102
  set ++= Set(999, 901)

  // Intersection
  val set1 = set & Set(10, 20, 30)
  println("Intersection")
  set1.foreach { println }

  // difference
  val set2 = set &~ set1
  println("Difference")
  set2.foreach { println }

  // HASH SET
  val hashSet = HashSet("Test1", "Test2", "Test3")
  hashSet += "Test4"
  hashSet -= "Test1"
  // the same functions & and &~

  // SORTED SET
  val sortedSet = SortedSet("a", "c", "B", "F", "A")
  sortedSet.foreach { println }
  // +=, ++=, -=, --=, &, &~, empty
  // change ordering
  object DescendingAlphabetOrdering extends Ordering[String] {
    override def compare(x: String, y: String): Int = {
      y.compareTo(x)
    }
  }
  val sortedSet2 =
    SortedSet("a", "c", "B", "F", "A")(DescendingAlphabetOrdering)
  println("After ordering")
  sortedSet2.foreach { println }

  // TREE SET
  // Red Black tree inside
  val treeSet = TreeSet(1, 2.0, 23.01, 31.023)
  // +=, ++=, -=, --=, &, &~, empty

  // LINKED HASH SET -> insertion order
  val linkedHashSet = LinkedHashSet(1, 30, 22, 199)
  linkedHashSet.foreach { println }

  // BIT SET
  val bitSet = BitSet(0, 2, 4, 6, 8)

}
