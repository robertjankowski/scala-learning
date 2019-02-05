package tourOfScala

object ForComprehensions extends App {

  case class User(name: String, age: Int)

  val userBase = List(User("Travis", 28),
                      User("Kelly", 33),
                      User("Jennifer", 44),
                      User("Dennis", 23))

  val twentySth = for {
    user <- userBase
    if (user.age >= 20) & (user.age < 30)
  } yield user.name
  println(twentySth.mkString(", "))

  def foo(n: Int, v: Int) =
    for {
      i <- 0 until n
      j <- 0 until v
      if i + j == v
    } yield (i, j)

  foo(10, 10).foreach {
    case (i, j) => println(s"($i, $j)")
  }

}
