package com.tutorial.chapter3

object CallBackFunctions extends App {
  def printReport(sendCallback: () => Unit): Unit = {
    println("Printing started ...")
    //
    println("Printing finished ...")
    sendCallback()
  }

  printReport(() => println("Sending email finished"))

  // Option callback
  def printReportOptionCallback(
      sendCallback: Option[() => Unit] = None): Unit = {
    println("Printing started ...")
    //
    println("Printing finished ...")
    sendCallback.map(e => e())
  }

  println(" ")
  printReportOptionCallback(Some(() => println("Oh boi !")))
  printReportOptionCallback()

}
