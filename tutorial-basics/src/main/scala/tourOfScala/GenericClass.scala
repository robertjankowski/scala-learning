package tourOfScala

class Stack[A] {
  private var elements = List.empty[A]
  def push(x: A) = elements = x :: elements
  def peek: A = elements.head
  def pop(): A = {
    val currentTop = peek
    elements = elements.tail
    currentTop
  }
}

abstract class Fruit1 {
  override def toString: String = s"${this.getClass}"
}
class Apple1 extends Fruit1
class Peach extends Fruit1

object GenericClass extends App {

  val stack = new Stack[Long]

  for (i <- 0 until 10) {
    stack.push(i)
  }
  println(stack.pop())
  println(stack.pop())
  println(stack.pop())

  val fruit = new Stack[Fruit1]
  fruit.push(new Apple1)
  fruit.push(new Peach)
  println(fruit.pop())
  println(fruit.pop())

}
