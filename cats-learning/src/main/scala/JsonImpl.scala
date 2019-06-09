sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json

final case class JsString(get: String) extends Json

final case class JsNumber(get: Double) extends Json

case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] = {
    (value: String) => JsString(value)
  }

  implicit val personWriter: JsonWriter[Person] = {
    (value: Person) =>
      JsObject(Map(
        "name" -> JsString(value.name),
        "email" -> JsString(value.email)
      ))
  }
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
}

object JsonSyntax {

  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }

}

object JsonImpl extends App {

  import JsonSyntax._
  import JsonWriterInstances._

  val jsonPerson = Json.toJson(Person("Rob", "rob@wp.pl"))
  println(jsonPerson)

  val jsonPerson1 = Person("Mike", "mike@example.pl").toJson
  println(jsonPerson1)

  val s = "Test".toJson
  println(s)
}
