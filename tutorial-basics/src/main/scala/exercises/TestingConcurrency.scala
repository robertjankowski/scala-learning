package exercises

import akka.actor.{Actor, Props, ActorSystem}
import scala.io.StdIn

// TODO:   https://doc.akka.io/docs/akka/current/guide/tutorial_1.html
// TODO: https://medium.com/@jstnlef/a-journey-into-concurrent-programming-in-scala-c1c3e7df0c4f
//  => interesting tutorial !
/*object PrintMyActorRefActor extends Actor {
  def props: Props = Props(new PrintMyActorRefActor)
}

class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" =>
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second $secondRef")
  }
}

object TestingConcurrency extends App {
  val system = ActorSystem("testSystem")
  val firstRef = system.actorOf(PrintMyActorRefActor.props, "first-actor")
  println(s"First: $firstRef")
  firstRef ! "printit"

  println(">>> Press ENTER to exit >>> ")
  try StdIn.readLine()
  finally system.terminate()
}
 */
