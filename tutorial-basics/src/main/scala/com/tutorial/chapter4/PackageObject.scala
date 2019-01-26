package com.tutorial.chapter4

object PackageObject extends App {
  val vanillaDonut: Donut = Donut("Vanilla", 40)
  println(vanillaDonut.uuid)

  val time = new DateTime()
  println(time)

}
