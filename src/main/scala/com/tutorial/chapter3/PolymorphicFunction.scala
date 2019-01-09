package com.tutorial.chapter3

object PolymorphicFunction extends App {
  def applyDiscount[T](discount: T): Seq[T] = {
    discount match {
      case e: String => Seq[T](discount)
      case e: Int =>
        val a: Int = e
        println(a.getClass.getCanonicalName)
        Seq[T](discount)
      case e @ _ =>
        println("unsupported discount")
        Seq[T](discount)
    }
  }

  val t = applyDiscount("10")
  t foreach println

  val a = applyDiscount(20)
  a foreach println

  val test = List(1, 2) ++ List(3, 4)
  test.foreach(e => println(e))

  def ? = "d"

  print(?)
}
