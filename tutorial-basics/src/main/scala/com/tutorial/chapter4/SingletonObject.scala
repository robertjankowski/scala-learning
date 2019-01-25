package com.tutorial.chapter4

object SingletonObject extends App {

  // create singleton object
  object DonutShoppingCartCalculator {
    val discount: Double = 0.01

    def calculateTotalCost(donuts: List[String]): Double = {
      donuts foreach println
      return 1
    }
  }

  println(s"Show global discount: ${DonutShoppingCartCalculator.discount}")

  println(
    s"${DonutShoppingCartCalculator.calculateTotalCost(List.fill(3)("Tob"))}")
}
