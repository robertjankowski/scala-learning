package com.tutorial.chapter6

object Maps extends App {

  // arrows or commas allowed
  val map1 = Map(("Rob" -> 1.302),
                 ("Bob", Math.E),
                 ("Mark", Math.PI),
                 ("Tom", Math.pow(Math.PI, Math.PI)))

  map1.foreach { println }

  // add element, arrows only
  val map2 = map1 + ("Cedric" -> Math.PI / Math.E)

  val map3 = map1 ++ map2
  println("Map1 + Map2")
  map3.foreach { println }

  // remove
  val map4 = map1 - ("Rob")

  // empty Map
  val map5 = Map.empty[String, Double]

}
