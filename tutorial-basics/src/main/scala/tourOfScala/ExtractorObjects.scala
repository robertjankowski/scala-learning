package tourOfScala

import scala.util.Random

object CustomerID {
  def apply(name: String) = s"$name--${Random.nextLong}"

  def unapply(customerID: String): Option[String] = {
    val stringArray: Array[String] = customerID.split("--")
    if (stringArray.tail.nonEmpty)
      Some(stringArray.head)
    else
      None
  }
}

object ExtractorObjects extends App {

  val customerID = CustomerID("Niko")
  println(customerID)

  customerID match {
    case CustomerID(name) => println(name)
    case _                => println("Could not extract a CustomerID")
  }

  val CustomerID(name) = customerID
  println(s"NAME: $name")

}
