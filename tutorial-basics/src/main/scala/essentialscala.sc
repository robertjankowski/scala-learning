println("Set up workspace")
val v = 3

123.toShort
256.toByte

"some string" split " " map(_.toUpperCase)


object Test {
  implicit val name = "Rob"
  def sayHello(implicit name: String) = println(s"Hello $name")

  val simpleField = {
    println("Evaluating simpleField")
    45
  }
  def noParamsMethod = {
    println("Evaluating noParamsMethod")
    44
  }
}
import Test._
Test.sayHello
Test.noParamsMethod

val a = if(1 < 3) "Yes" else "No"
