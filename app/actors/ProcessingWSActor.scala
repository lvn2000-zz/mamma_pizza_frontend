package actors

import scala.concurrent.duration.DurationInt

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.util.Timeout
import utils.FrontendSettings

class ProcessingWSActor(out: ActorRef) extends Actor with ActorLogging {

  implicit val askTimeout: Timeout = FrontendSettings.askTimeOutSeconds.seconds

  def receive = {
    case json: String ⇒ out ! json

    case _            ⇒
  }
}

object ProcessingWSActor {
  def props(out: ActorRef) = Props(new ProcessingWSActor(out))
}



