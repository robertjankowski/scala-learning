package tourOfScala

trait Buffer {
  type T
  val element: T
}

abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]
  def length = element.length
}

abstract class IntSeqBuffer extends SeqBuffer {
  type U = Int
}

object AbstractTypeMembers extends App {

  def newIntSeqBuffer(elem1: Int, elem2: Int): IntSeqBuffer = {
    new IntSeqBuffer {
      override type T = List[U]
      override val element = List(elem1, elem2)
    }
  }

  val buf = newIntSeqBuffer(10, 20)
  println(s"length: ${buf.length}")
  println(s"content: ${buf.element}")

}
