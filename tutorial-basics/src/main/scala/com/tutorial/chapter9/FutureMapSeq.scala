package com.tutorial.chapter9

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

object FutureMapSeq extends App {

  def donutStock(donut: String): Future[Option[Int]] = Future {
    println("checking donut stock")
    if (donut == "vanilla donut") Some(10) else None
  }

  Thread.sleep(1000)
  donutStock("vanilla donut").map(sth =>
    println(s"Buying ${sth.getOrElse(0)} vanilla donuts"))

  // Future sequence
  def longOperation(value: Double): Future[Option[Int]] = Future {
    println("Starting long operation")
    Thread.sleep(1000)
    if (value < 10) Some(10) else None
  }

  def longOperationWithStatus(quantity: Int): Future[Boolean] = Future {
    println("Long operation with status")
    Thread.sleep(2000)
    if (quantity > 5) true else false
  }

  def processOperation(): Future[Unit] = Future {
    println("Processing operations...")
    Thread.sleep(1000)
  }

  val listOfOperations =
    List(longOperation(10.54), longOperationWithStatus(22), processOperation())

  // run in parallel
  val futureSeqResults = Future.sequence(listOfOperations)
  futureSeqResults.onComplete {
    case Success(value) => println(s"Results: $value")
    case Failure(exception) =>
      println(s"Error processing operations: $exception")
  }
}
