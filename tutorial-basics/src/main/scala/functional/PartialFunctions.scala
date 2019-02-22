package functional

object PartialFunctions extends App {

  val g: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  val a = g.isDefinedAt(List(1, 2, 3))
  println(a)
}
