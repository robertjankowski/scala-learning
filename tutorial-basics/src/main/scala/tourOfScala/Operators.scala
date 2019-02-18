package tourOfScala

object Operators extends App {
  // operators are methods
  println(10.+(1))

  case class Vec(val x: Double, val y: Double) {
    def +(that: Vec) = new Vec(this.x + that.x, this.y + that.y)
  }
  val vec1 = Vec(10, 20)
  val vec2 = Vec(-3, 20.4)
  println(vec1 + vec2)
  println(vec1.+(vec2))

  case class MyBool(x: Boolean) {
    def and(that: MyBool): MyBool = if (x) that else this
    def or(that: MyBool): MyBool = if (x) this else that
    def negate: MyBool = MyBool(!x)
  }

  def not(x: MyBool): MyBool = x.negate
  def xor(x: MyBool, y: MyBool): MyBool = (x or y) and not(x and y)

  println(xor(MyBool(true), MyBool(true)), xor(MyBool(true), MyBool(false)))

  // By name parameters
  def calculate(input: => Int) = input * 10
  // implementation of while loop
  def whileLoop(condition: => Boolean)(body: => Unit): Unit = {
    if (condition) {
      body
      whileLoop(condition)(body)
    }
  }
  var i = 2
  whileLoop(i > 0) {
    println(i)
    i -= 1
  }
}
