package com.tutorial.chapter9

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._ // for `seconds`
import scala.util.{Failure, Success}

object FutureIntro extends App {

  def productCost(position: Int): Future[Int] = Future {
    position * 2
  }

  // blocking example
  /*
  val desk = Await.result(productCost(2), 2.seconds)
  println(s"Price for desk: $desk")
   */

  // asynchronous call
  productCost(20).onComplete {
    case Success(price) => println(s"Price: $price")
    case Failure(e)     => println(s"Failed ${e.getMessage}")
  }
  Thread.sleep(1000) // block main thread

  def isExpensive(cost: Int): Future[Boolean] = Future {
    cost > 10
  }

  val desk = productCost(20).flatMap(isExpensive)
  val isDeskExpensive = Await.result(desk, 1.seconds)
  println(s"Desk expensive: ${if (isDeskExpensive) "Yes" else "No"}")

  // for comprehension
  for {
    cost <- productCost(2)
    cheap <- isExpensive(cost)
  } yield println(s"Product is $cheap")

}
