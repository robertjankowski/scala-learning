package functional

abstract class IntSet {
  def include(x: Int): IntSet
  def contains(x: Int): Boolean
}

object Empty extends IntSet {
  override def contains(x: Int): Boolean = false

  override def include(x: Int): IntSet = NonEmpty(x, Empty, Empty)
}

case class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  override def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  override def include(x: Int): IntSet = {
    if (x < elem) NonEmpty(elem, left include x, right)
    else if (x > elem) NonEmpty(elem, left, right include x)
    else this
  }

}

/**
  * Lesson 2.1 (IntSet)
  */
object Trees extends App {
  //
}
