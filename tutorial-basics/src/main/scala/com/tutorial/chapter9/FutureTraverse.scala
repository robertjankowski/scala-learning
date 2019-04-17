package com.tutorial.chapter9

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

object FutureTraverse extends App {

  def longOperation(value: Double): Future[Option[Int]] = Future {
    println("Starting long operation")
    Thread.sleep(1000)
    if (value < 10) Some(10) else None
  }

  val futureOperations =
    List(longOperation(10.1),
         longOperation(20.222),
         longOperation(0.12),
         longOperation(43.2))

  val futureResults: Future[List[Int]] = Future.traverse(futureOperations) {
    f =>
      f.map(s => s.getOrElse(0))
  }

  var l = List.empty[Int]
  futureResults.onComplete {
    case Success(value) => l = value
    case Failure(_)     => println("Error in future results")
  }

  println(s"Results list: ${l.mkString("|")}")
}
