package com.tutorial.chapter5

trait Books[A <: String] {

  def author(book: A): String

  def year(book: A): Long
}

class MyBook[A <: String] extends Books[A] {
  override def author(book: A): String = "Test author"

  override def year(book: A): Long = 1997

}

object TrainWithType extends App {
  // similar to generic interface in Java

  // this example is useless...
  type Book = String
  val myBook = new MyBook[String]
  val name: Book = "1 2 3"
  println(myBook.author(name))
  println(myBook.year(name))
}
