package tourOfScala

trait Eatable {
  def name: String
}

abstract class Food extends Eatable

class Bread extends Food {
  override def name: String = "Bread"
}

class Cheese extends Food {
  override def name: String = "Cheese"
}

class Milk extends Eatable {
  override def name: String = "Milk"
}

class FoodContainer[A <: Food](f: A) {
  def food: A = f
}

object UpperTypeBounds extends App {

  val breadContainer = new FoodContainer[Bread](new Bread)
  val cheeseContainer = new FoodContainer[Cheese](new Cheese)

  println(breadContainer.food.name)
  println(cheeseContainer.food.name)

  // would not compile
  //val milkContainer = new FoodContainer[Milk](new Milk)

}
