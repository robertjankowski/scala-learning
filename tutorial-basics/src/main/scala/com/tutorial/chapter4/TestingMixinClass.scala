package com.tutorial.chapter4

abstract class AbsIterator {
  type T

  def hasNext: Boolean

  def next(): T
}

class StringIterator(s: String) extends AbsIterator {
  type T = Char
  private var i = 0

  override def hasNext: Boolean = i < s.length

  override def next(): Char = {
    val ch = s charAt i
    i += 1
    ch
  }
}

trait RichIterator extends AbsIterator {
  def foreach(f: T => Unit): Unit = while (hasNext) f(next())
}

class IntIterator(i: Int) extends StringIterator(i.toString) with RichIterator

object TestingMixinClass extends App {

  class RichStringIterator extends StringIterator("Scala") with RichIterator

  val richStringIterator = new RichStringIterator
  richStringIterator foreach println

  val intIterator = new IntIterator(1997)
  intIterator foreach println
}
