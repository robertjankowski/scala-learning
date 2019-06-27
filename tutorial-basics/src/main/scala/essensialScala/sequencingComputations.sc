object generics {
  final case class Message[A](message: A)
  def generic[A](value: A) = value
}

generics.generic("Tom") // infer type
generics.Message(1)


object linkedList {
  sealed trait LinkedList[A] {
    def fold[B](end: B)(pair: (A, B) => B): B =
      this match {
        case End() => end
        case Pair(hd, tl) => pair(hd, tl.fold(end)(pair))
      }

    def length: Int =
      fold(0)((_, tl) => 1 + tl)

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

// functions literals
val sayHi = () => "Hi!"
sayHi()

val add1 = (x: Int) => x + 1
add1(4)

((_: Int) * 2)(2)

object pair {
  case class Pair[A, B](one: A, two: B)
}

import pair.Pair
val p = Pair[String, Int]("hi" , 2)
p.one
p.two

(1, "A") match {
  case (a, b) => a + b
}

object maybe {

  sealed trait Maybe[+A] {
    def fold[B](full: A => B)(empty: B): B =
      this match {
        case Full(value) => full(value)
        case Empty => empty
      }

    def map[B](f: A => B): Maybe[B] =
      flatMap[B](v => Full(f(v)))

    def flatMap[B](f: A => Maybe[B]): Maybe[B] =
      this match {
        case Full(value) => f(value)
        case Empty => Empty
      }
  }
  case object Empty extends Maybe[Nothing]

  final case class Full[A](value: A) extends Maybe[A]

}
import maybe._
val perhaps: Maybe[Int] = Full(1)
perhaps.fold(_ + 1)(10)
perhaps.map(_ * 2)
perhaps.flatMap(v => Full(v + 10))

val empty: Maybe[Int] = Empty
empty.fold(_ + 2)(1)

val list: List[Maybe[Int]] = List(Full(3), Full(2), Full(1))
list.map(maybe => maybe.flatMap[Int] { x => if (x % 2 == 0) Full(x) else Empty })

