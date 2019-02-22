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

abstract class Fruit {
  override def toString: String = s"${this.getClass}"
}
class Apple extends Fruit
class Peach extends Fruit

object GenericClass extends App {

  val stack = new Stack[Long]

  for (i <- 0 until 10) {
    stack.push(i)
  }
  println(stack.pop)
  println(stack.pop)
  println(stack.pop)

  val fruit = new Stack[Fruit]
  fruit.push(new Apple)
  fruit.push(new Peach)
  println(fruit.pop)
  println(fruit.pop)

}
