package com.tutorial.chapter4

object ValuesAndFields extends App {

  class Donut(name: String, productCode: Option[Long] = None) {
    def print =
      println(s"Name: $name, productCode: ${productCode
        .getOrElse("None code")}, donut id: ${Donut.uuid}")
  }

  object Donut {
    private val uuid = 1L

    def apply(name: String, productCode: Option[Long] = None): Donut =
      new Donut(name, productCode)

    def apply(name: String): Donut = new Donut(name)
  }

  val glazedDonut = Donut("Glazed Donut", Some(1111))
  val vanillaDonut = Donut("Vanilla Donut")
  glazedDonut.print
  vanillaDonut.print

  // testing
  abstract class AbstractTest

  class StringTest(s: String) extends AbstractTest

  class IntTest(i: Int) extends AbstractTest

  object AbstractTest {
    def apply(s: String): AbstractTest = {
      new StringTest(s)
    }

    def apply(i: Int): AbstractTest = {
      new IntTest(i)
    }
  }

  val intTest = AbstractTest(10)
  val stringTest = AbstractTest("10")
  println(s"intTest: ${intTest.getClass}, stringTest: ${stringTest.getClass}")

}
