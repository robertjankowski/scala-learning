package tourOfScala

object PolymorphicMethods extends App {

  def listOfDuplicates[A](x: A, length: Int): List[A] = {
    if (length < 1)
      List.empty
    else
      x :: listOfDuplicates(x, length - 1)
  }
  println(listOfDuplicates[Int](3, 10))
  println(listOfDuplicates("a", 5))

  // Omitting types - type inference
  case class MyPair[A, B](x: A, y: B)
  val p = MyPair(1, "scala")
  def id[T](x: T) = x
  val q = id(193.0)

  // in recursive functions need to pass the type
  def fib(x: Int): Int = {
    if (x == 0)
      1
    else
      x * fib(x - 1)
  }
  println(fib(10))

}
