package com.tutorial.chapter5

object DepInjFacade extends App {

  class DonutInventoryService[A] {
    def checkStock(donut: A): Boolean = {
      println("$$$ DonutInventoryService $$$ -> checkStock")
      true
    }
  }

  class DonutPricingService[A] {
    def calculatePrice(donut: A): Double = {
      println("$$$ DonutPricingService $$$ -> calculatePrice")
      2.4
    }
  }

  class DonutOrderService[A] {
    def createOrder(donut: A, quality: Int, price: Double): Int = {
      println("$$$ DonutOrderService $$$ -> createOrder")
      100
    }
  }

  class DonutShoppingCartService[A](
      donutInventoryService: DonutInventoryService[A],
      donutPricingService: DonutPricingService[A],
      donutOrderService: DonutOrderService[A]) {

    def bookOrder(donut: A, quantity: Int): Int = {
      println("$$$ DonutShoppingCartService $$$ -> bookOrder")
      donutInventoryService.checkStock(donut) match {
        case true =>
          val price = donutPricingService.calculatePrice(donut)
          donutOrderService.createOrder(donut, quantity, price)
        case false =>
          println(s"Sorry donut $donut is out of stock")
          404
      }
    }

  }

  // encapsulate all in trait
  trait DonutStoreService {
    val donutInventoryService = new DonutInventoryService[String]
    val donutPricingService = new DonutPricingService[String]
    val donutOrderService = new DonutOrderService[String]
    val donutShoppingCartService = new DonutShoppingCartService[String](
      donutInventoryService,
      donutPricingService,
      donutOrderService)
  }

  // create facade
  trait DonutAppController {
    this: DonutStoreService =>
    def bookOrder(donut: String, quantity: Int): Int = {
      println("$$$ DonutAppController $$$ -> bookOrder")
      donutShoppingCartService.bookOrder(donut, quantity)
    }
  }

  object DonutStoreApp extends DonutAppController with DonutStoreService

  DonutStoreApp.bookOrder("Scala programming", 1)

}
