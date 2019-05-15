package tourOfScala

trait Node[+A] {
  def prepend[U >: A](elem: U): Node[U]
}

case class ListNode[+A](h: A, t: Node[A]) extends Node[A] {
  override def prepend[U >: A](elem: U): ListNode[U] = ListNode(elem, this)
}

case class Nil[+A]() extends Node[A] {
  override def prepend[U >: A](elem: U): ListNode[U] = ListNode(elem, this)
}

trait Bird
case class AfricanSwallow() extends Bird
case class EuropeanSwallow() extends Bird

object LowerTypeBounds extends App {

  val africanSwallowList = ListNode[AfricanSwallow](AfricanSwallow(), Nil())

  val birdList: Node[Bird] = africanSwallowList
  val newBirdList = birdList.prepend(new EuropeanSwallow)
  println(newBirdList)

}
