package communication

import scala.util.Random


trait Notify {
  def message(message: String): Unit
}

abstract class Message(person1: String, person2: String, length: Long) extends Notify {

  val speaker = person1
  val listener = person2
  val time = length

  override def message(message: String): Unit = {
    println(s"Speaker $speaker said $message to $listener (time = $time)")
  }

  override def toString: String = s"speaker: $speaker\tlistener: $listener (time = $time)"
}


class SMS(person1: String, person2: String, length: Long)
  extends Message(person1, person2, length) {

  override def message(message: String): Unit = {
    println(s"$speaker send SMS: $message to $listener")
  }

}

class Email(person1: String, person2: String, length: Long)
  extends Message(person1, person2, length) {

  override def message(message: String): Unit = {
    println(s"$speaker send Email: $message to $listener")
  }

}

object Message {
  def apply(person1: String, person2: String, kind: String): Option[Message] = kind match {
    case "SMS" => Some(new SMS(person1, person2, Random.nextInt(100)))
    case "Email" => Some(new Email(person1, person2, Random.nextInt(100)))
    case _ =>
      println("Unknown message type")
      None
  }
}

class Chat {

  var channels = Vector.empty[Message]

  def createChannel(channel: Message): Long = {
    channels = channel +: channels
    channels.size
  }

  def sortChannel(by: String): Unit = by match {
    case "speaker" => channels = channels.sortBy(channel => channel.speaker)
    case "listener" => channels = channels.sortBy(channel => channel.listener)
    case "time" => channels = channels.sortBy(channel => channel.time).reverse
    case _ => println("Unknown sorting by parameters. Abort")
  }

  def showChannels: Unit = channels.foreach { println }

}

object Notification extends App {

  val sms = Message("Rob", "Tom", "SMS").get
  sms.message("Hello !")
  println(sms)

  val email = Message("Bob", "Mark", "Email").get
  email.message("Hello once again")

  val unknownMessage = Message("Lucy", "Jane", "MESSAGE")
  assert(unknownMessage == None)

  // create simple chat
  println("\n****CHAT****\n")
  val chat = new Chat
  chat.createChannel(email)
  chat.createChannel(sms)
  chat.createChannel(Message("Paul", "Jake", "SMS").get)

  println("Before sorting by listener")
  chat.showChannels

  println("\nAfter sorting by listener")
  chat.sortChannel("listener")
  chat.showChannels

  println("\nAfter sorting by time")
  chat.sortChannel("time")
  chat.showChannels

}
