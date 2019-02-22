package functional

import scala.util.Random

trait Generator[+T] {
  self =>

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }

}

/**
  * 1.3 Functional Random Generator
  */
object FunctionalRandomGen extends App {

  // first solution
  val integers = new Generator[Int] {
    val rand = new Random
    override def generate: Int = rand.nextInt()
  }

  val booleans = new Generator[Boolean] {
    override def generate: Boolean = integers.generate > 0
  }

  val pairs = new Generator[(Int, Int)] {
    override def generate: (Int, Int) = (integers.generate, integers.generate)
  }

  // functional solution
  val booleans1 = for (x <- integers) yield x > 0

  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate = x
  }

  def choose(lo: Int, hi: Int): Generator[Int] = {
    for (x <- integers) yield lo + x % (hi - lo)
  }

  /// Tree
  trait Tree

  case class Inner(left: Tree, right: Tree) extends Tree

  case class Leaf(x: Int) extends Tree

  def leafs: Generator[Leaf] =
    for {
      x <- integers
    } yield Leaf(x)

  def inners: Generator[Inner] =
    for {
      l <- trees
      r <- trees
    } yield Inner(l, r)

  def trees: Generator[Tree] =
    for {
      isLeaf <- booleans1
      tree <- if (isLeaf) leafs else inners
    } yield tree

  println(trees.generate)

  // Random Test Function
  def test[T](g: Generator[T], numTimes: Int = 100)(
      test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for " + value)
    }
    println("passed " + numTimes + " tests")
  }

  val test1 = (x: Int) => x > 10
  // test(integers, numTimes = 10)(test1)

}
