package com.tutorial.chapter8

object GroupByIntersect extends App {
  //
  val seq = Seq("One", "Two", "Three", "Four", "Five")

  val byName = seq.groupBy(_.charAt(0))
  byName.foreach { println }

  case class Person(name: String, age: Int)
  val people = List(Person("Jack", 20),
                    Person("Mike", 20),
                    Person("Tom", 12),
                    Person("Mike", 14))
  people.groupBy(_.age).foreach { println }

  println(people.isEmpty)

  // intersect
  val set1 = Set(1, 2, 3)
  val set2 = Set(2, 3)
  println(set1.intersect(set2).mkString(","))
  println((set1 & set2).mkString(","))

}
