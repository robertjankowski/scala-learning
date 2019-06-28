import scala.collection.immutable.Queue

// Seq
val s = Seq(1, 2, 3, 10, 23)
s(1)
s.head
s.tails.foreach(println)
s.find(_ % 2 == 0) // first element
s.filter(_ % 2 == 0)

s.sortWith(_ > _)
s :+ 4   // append to tail
s.+:(44) // append to head
44 +: s  // the same
s ++ Seq(999, 204)

// List
Nil
val l = 1 :: 2 :: 3 :: Nil
233 :: l
List(1, 33) ::: List(33, 41)

Queue(33, 33)

s.map(_ + 2).filterNot(_ < 5).drop(1)

Seq("the", "tree")
  .flatMap(_.permutations.toList)
  .mkString(", ")

Vector(1, 2, 3).flatMap(num => Seq(num, num * 10))

val res = (1 :: 4 :: 402 :: Nil).foldLeft(1)((a, b) => a - b)
assert(res == -406)

def min(s: Seq[Int]): Int = s.sortWith(_ < _).head
def smallest(s: Seq[Int]): Int = s.foldLeft(Int.MaxValue)(math.min)
assert(min(Seq(2, 3, 4, 1)) == smallest(Seq(2, 3, 4, 1)))

def insert(s: Seq[Int], elt: Int): Seq[Int] = {
  if (s.contains(elt))
    s
  else
    elt +: s
}
def unique(s: Seq[Int]) = s.foldLeft(Seq.empty[Int]) { insert }
unique(Seq(1, 1, 2, 3, 4, 4, 4))

// for comprehensions
for {
    x <- 1 to 3
    y <- 2 to 3
} yield x * 2 + y

val data = Seq(Seq(1), Seq(2, 3), Seq(4, 5, 6))
for {
    s <- data
    elem <- s
} yield elem

// option
def readInt(str: String): Option[Int] = {
    if (str matches "-?\\d+")
        Some(str.toInt)
    else
        None
}
readInt("1233") match {
    case Some(v) => println(v)
    case None => println("None")
}

def sum(x: Option[Int], y: Option[Int]): Option[Int] =
    for {
        xx <- x
        yy <- y
    } yield xx + yy

sum(readInt("123"), readInt("44"))