package controllers

import actors.ProcessingWSActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.Inject
import play.api.libs.streams.ActorFlow
import play.api.mvc.{AbstractController, ControllerComponents, WebSocket}

class MainWSController @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer)
  extends AbstractController(cc) {

  def wsSocket = WebSocket.accept[String, String] { request ⇒
    ActorFlow.actorRef { out ⇒
      ProcessingWSActor.props(out)
    }

    //      Try {
    //        request.getQueryString("request")
    //        read[PizzaMessageRequest](json)
    //      } match {
    //        case Success(req) => (processingdActor ? req).map(resp => write[PizzaMessageResponse](resp)) pipeTo out
    //        case Failure(e)   => log.error(e.getMessage)
    //      }

  }

}
