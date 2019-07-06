package rabbitmq.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import rabbitmq.actor.QueueListener.{CloseYourEyes, Listen}
import rabbitmq.actor.Supervisor.{Begin, End}

object Supervisor {

  case object Begin

  case object End

  def props: Props = Props[Supervisor]
}

class Supervisor extends Actor {

  val queueListener: ActorRef = context.actorOf(QueueListener.props)

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case _: Exception => Stop
  }

  override def receive: Receive = {
    case Begin => queueListener ! Listen
    case End => queueListener ! CloseYourEyes
  }
}