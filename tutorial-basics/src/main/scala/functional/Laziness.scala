package functional

/**
  * 2.3 Lesson
  */
object Laziness extends App {

  def expr = {
    val x = { println("x"); 1 }
    lazy val y = { println("y"); 2 }
    def z = { println("z"); 3 }
    z + y + x + z + y + x
  }
  expr
  //  x
  //  z
  //  y
  //  z

}
