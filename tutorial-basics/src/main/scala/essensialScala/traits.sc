import java.util.Date

sealed trait Visitor {
  def id: String
  def createdAt: Date
  def age: Long = new Date().getTime - createdAt.getTime
}

final case class Anonymous(
    id: String,
    createdAt: Date = new Date()
) extends Visitor

final case class User(
    id: String,
    email: String,
    createdAt: Date = new Date()
) extends Visitor

object Test {
  def older(v1: Visitor, v2: Visitor): Boolean =
    v1.createdAt.before(v2.createdAt)
}

Test.older(Anonymous("1"), User("2", "@gmail.com"))

sealed trait DivisionResult
final case class Finite(value: Int) extends DivisionResult
case object Infinite extends DivisionResult

object divide {
  def apply(x: Int, y: Int) :DivisionResult = y match {
    case 0 => Infinite
    case _ => Finite(x / y)
  }
}

divide(2, 0)
divide(3, 1)

object Traffic {

  sealed trait TrafficLight {
    def next: TrafficLight =
      this match {
        case Red => Green
        case Green => Yellow
        case Yellow => Red
      }
  }

  case object Red extends TrafficLight

  case object Green extends TrafficLight

  case object Yellow extends TrafficLight

}

object TrafficTest {
  import Traffic._
  val r = Red
  val g = r.next
  val y = g.next
  println(s"$y")
}
TrafficTest

object IntListImpl {

  sealed trait IntList {
    def length: Int =
      this match {
        case End => 0
        case Pair(_, tl) => 1 + tl.length
      }

    def double: IntList =
      this match {
        case End => End
        case Pair(hd, tl) => Pair(2 * hd, tl.double)
      }
  }

  case object End extends IntList

  final case class Pair(head: Int, tail: IntList) extends IntList

  def sum(list: IntList): Int = list match {
    case End => 0
    case Pair(hd, tl) => hd + sum(tl)
  }
}

object IntListExample {
  import IntListImpl._

  val example = Pair(1, Pair(2, Pair(3, End)))
  val result = sum(example)

  val len = example.length

  val doubleVals = example.double

}
println(s"Sum of example: ${IntListExample.result}")
println(s"Length of example: ${IntListExample.len}")
println(s"Double of exampple: ${IntListExample.doubleVals}")
