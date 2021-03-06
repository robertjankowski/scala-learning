import java.util.Date

implicit val minOrdering = Ordering.fromLessThan[Int](_ < _)

List(3, 4, 4, 1, 345, 3).sorted

val absOrdering = Ordering.fromLessThan[Int]{
  (a, b) => math.abs(a) < math.abs(b)
}
assert(List(-4, -1, 0, 2, 3).sorted(absOrdering) == List(0, -1, 2, 3, -4))
assert(List(-4, -3, -2, -1).sorted(absOrdering) == List(-1, -2, -3, -4))

final case class Rational(numerator: Int, denominator: Int)

object Rational {
  implicit val ordering = Ordering.fromLessThan[Rational]((x, y) =>
    (x.numerator.toDouble / x.denominator.toDouble) <
      (y.numerator.toDouble / y.denominator.toDouble)
  )
}

object Example {
  def example() = assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted ==
    List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))
}

trait HtmlWriter[A] {
  def write(in: A): String
}

object HtmlWriter {
  def apply[A](implicit writer: HtmlWriter[A]): HtmlWriter[A] = writer
}

final case class Person(name: String, email: String)

object Person {
  implicit object PersonWriter extends HtmlWriter[Person] {
    override def write(person: Person) =
      s"<span>${person.name} &lt;${person.email}&gt;</span>"
  }
}

object DateWriter1 extends HtmlWriter[Date] {
  def write(in: Date) = s"<span>${in.toString}</span>"
}

object ObfuscatedPersonWriter extends HtmlWriter[Person] {
  def write(person: Person) =
    s"<span>${person.name} (${person.email.replaceAll("@", " at ")})</span>"
}

implicit class HtmlOps[T](data: T) {
  def toHtml(implicit writer: HtmlWriter[T]) =
    writer.write(data)
}

DateWriter1.write(new Date)
ObfuscatedPersonWriter.write(Person("John", "john@example.com"))

HtmlWriter[Person].write(Person("Noel", "noel@example.org"))

Person("John", "john@example.com").toHtml

implicit class ExtraStringMethods(str: String) {
  val vowels = Seq('a', 'e', 'i', 'o', 'u')

  def numberOfVowels = str.toList.count(vowels contains _)
}
"the quick brown fox".numberOfVowels

implicit class IntYeah(count: Int) {
  def yeah() =
    for {
      _ <- 1 to count
    } println("Oh yeah")

}
2.yeah()
(-1).yeah()

// Context bound syntax
def pageTemplate[A: HtmlWriter](body: A): String = {
  val renderedBody = body.toHtml

  s"<html><head>...</head><body>$renderedBody</body></html>"
}

case class Example(name: String)
implicit val ex: Example = Example("implicit")

implicitly[Example] == ex

class B {
  def bar = "class B method"
}
class A

implicit def aToB(in: A): B = new B()

new A().bar