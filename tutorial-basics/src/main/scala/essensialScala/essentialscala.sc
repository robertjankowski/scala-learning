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
import java.util.Date

import Test._
Test.sayHello
Test.noParamsMethod

val a = if(1 < 3) "Yes" else "No"

class Person(val firstName: String, val lastName: String) {
  def name = firstName + " " + lastName
}

val p1 = new Person("Mike", "Smith")
p1.name

List(1, "Tom") // Any type

object LongName {
  def hasLongName(p: Person) = p match {
    case _ if p.name.length > 10 => true
    case _ => false
  }
}
LongName.hasLongName(p1)


class Counter(val count: Int) {
  def dec: Counter = dec()
  def inc: Counter = inc()
  def dec(amount: Int = 1) = new Counter(count - amount)
  def inc(amount: Int = 1) = new Counter(count + amount)
}
new Counter(10).inc.dec.inc(12).count

class Adder(amount: Int) {
  def apply(in: Int): Int =  in + amount
}
val a1 = new Adder(30)
a1(20) // or a1.apply(20)

class Timestamp(val seconds: Long)

object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
    new Timestamp(hours*60*60 + minutes*60 + seconds)
}

Timestamp(1, 1, 1).seconds


case class Fruit(name: String)
Fruit("apple")

