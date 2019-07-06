package rabbitmq.model

import play.api.libs.json.Json

case class Person(name: String, age: Int)

object Person {
  implicit def personFormat = Json.format[Person]
}

