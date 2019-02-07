package functional

object Streams extends App {

  def streamRange(lo: Int, hi: Int): Stream[Int] = {
    if (lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
  }

  def listRange(lo: Int, hi: Int): List[Int] = {
    if (lo >= hi) Nil
    else lo :: listRange(lo + 1, hi)
  }

  val streamRange: Stream[Int] = streamRange(1, 100)
  val listRange: List[Int] = listRange(1, 100)

  println(streamRange.tail)
  println(listRange.tail)
}
