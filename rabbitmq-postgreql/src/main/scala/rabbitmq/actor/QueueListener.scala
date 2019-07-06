package rabbitmq.actor

import java.io.{ByteArrayInputStream, ObjectInputStream}

import akka.actor.{Actor, ActorLogging, Props}
import com.spingo.op_rabbit._
import com.typesafe.config.ConfigFactory
import rabbitmq.actor.QueueListener.{CloseYourEyes, Listen}
import rabbitmq.model.Person

object QueueListener {

  case object Listen

  case object CloseYourEyes

  def props = Props(new QueueListener)
}

class QueueListener extends Actor with ActorLogging {

  val conf = ConfigFactory.load()
  val QUEUE = conf.getString("op-rabbit.my-queue")

  val RABBIT_CONTROL = context.actorOf(Props[RabbitControl])

  var myQueueSubscription: Option[SubscriptionRef] = None

  implicit val personMarshaller = new RabbitMarshaller[Person] with RabbitUnmarshaller[Person] {
    override def marshall(value: Person): Array[Byte] = value.toString.getBytes()

    override protected def contentType: String = "text/plain"

    override protected def contentEncoding: Option[String] = Some("UTF-8")

    override def unmarshall(value: Array[Byte], contentType: Option[String], contentEncoding: Option[String]): Person = {
      val in = new ObjectInputStream(new ByteArrayInputStream(value))
      val person = in.readObject().asInstanceOf[Person]
      person
    }
  }

  override def receive: Receive = {
    case Listen =>
      import com.spingo.op_rabbit.Directives._

      import scala.concurrent.ExecutionContext.Implicits.global
      implicit val recoveryStrategy = RecoveryStrategy.nack()
      myQueueSubscription = Some(
        Subscription.run(RABBIT_CONTROL) {
          channel(qos = 3) {
            consume(queue(QUEUE)) {
              body(as[Person]) {
                person =>
                  log.debug(s"Received person = $person")
                  ack()
              }
            }
          }
        }
      )
    case CloseYourEyes => myQueueSubscription.foreach(_.close())
  }
}



