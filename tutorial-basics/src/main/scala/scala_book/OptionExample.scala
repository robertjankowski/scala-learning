package scala_book

object OptionExample extends App {

  val person1 = Some("Rob")
  val person2 = None
  val person3 = Some("Long name")
  showPerson(person1)
  showPerson(person2) // not shown
  val people = List(person1, person2, person3)
  val mass = Some(10.2)
  val acceleration = Some(9.81)
  people.foreach(showPersonWithLongName)

  def showPerson(person: Option[String]): Unit = {
    for (p <- person) {
      println(s"Person: $p")
    }
  }

  def showPersonWithLongName(person: Option[String]): Unit = {
    for {
      p <- person
      if p.length > 4
    } {
      println(s"Person with long name: $p")
    }
  }

  // handle potentially missing values while creating different variable
  def calculateForce(mass: Option[Double],
                     acceleration: Option[Double]): Option[Double] = {
    for {
      m <- mass
      a <- acceleration
    } yield m * a
  }

  println(calculateForce(mass, None)) // None
  println(calculateForce(mass, acceleration))

}
