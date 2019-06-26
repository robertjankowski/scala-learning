object generics {
  final case class Message[A](message: A)
  def generic[A](value: A) = value
}

generics.generic("Tom") // infer type
generics.Message(1)

object linkedList {
  sealed trait LinkedList[A] {
    def length: Int =
      this match {
        case End() => 0
        case Pair(_, tl) => 1 + tl.length
      }

    def contains(value: A): Boolean =
      this match {
        case End() => false
        case Pair(hd, tl) =>
          if (hd == value)
            true
          else
            tl.contains(value)
      }

    def apply(index: Int): Result[A] =
      this match {
        case Pair(hd, tl) =>
          if(index == 0)
            Success(hd)
          else
            tl(index - 1)
        case End() =>
          Failure("Attempted to get element from an Empty list")
      }
  }

  final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
  final case class End[A]() extends LinkedList[A]

  sealed trait Result[A]
  case class Success[A](result: A) extends Result[A]
  case class Failure[A](reason: String) extends Result[A]

  val ll = Pair(1, Pair(3, End()))
  println(s"Length of ll ${ll.length}")

}
import linkedList.{End, Pair}
val example1 = linkedList.ll
assert(example1.contains(3))
assert(!example1.contains(4))
assert(!End().contains(0))

