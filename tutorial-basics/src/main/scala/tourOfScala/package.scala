package object tourOfScala {
  abstract class Fruit(name: String, color: String) {
    override def toString: String = s"$name - $color"
  }
  object Apple extends Fruit("Apple", "green")
  object Banana extends Fruit("Banana", "yellow")
  val fruit = List(Apple, Banana)
  def showFruit(fruit: List[Fruit]): Unit = {
    fruit.foreach(println)
  }
}
