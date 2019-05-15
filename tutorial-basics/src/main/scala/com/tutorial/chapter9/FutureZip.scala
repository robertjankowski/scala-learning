package com.tutorial.chapter9

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object FutureZip extends App {

  def getQuantity(name: String): Future[Option[Int]] = Future {
    println("Get quantity...")
    name match {
      case "Volvo" => Some(2)
      case "Ford"  => Some(10)
      case _       => None
    }
  }

  def price(): Future[Double] = Future.successful(10.10)

  val zippedFuncs = getQuantity("Volvo") zip price()
  zippedFuncs.onComplete {
    case Success(value)     => println(s"Results: $value")
    case Failure(exception) => println(exception.getMessage)
  }
  Await.result(zippedFuncs, 5 seconds)

}
