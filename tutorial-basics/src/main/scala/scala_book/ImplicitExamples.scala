package scala_book

object ImplicitExamples extends App {

  object A {
    implicit val y = 5
  }

  object B {
    implicit val y = 10
  }

  def print(implicit x: Int) = println(addAndSquare(x))

  def testImplicit() = {
    import A.y
    print
  }
  testImplicit()

  import scala.math.sqrt
  def addAndSquare = (x: Int) => sqrt(x + x)

  def message(msg: String) = println(msg)
  implicit def intToString(x: Int) = x.toString
  message(5)

  def testConversion(x: List[Int]) = println(x.mkString(", "))
  implicit class ListOf[A](val list: List[A]) {
    def of[B](implicit f: A => B): List[B] = list map f
  }
  implicit def stringToInt(x: String) = x.length
  testConversion(List("rob", "jan", "test")) // not working...

}
