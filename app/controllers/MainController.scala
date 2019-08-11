package controllers

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.util.Timeout
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.FrontendSettings
import akka.pattern._
import models.{PizzaListFrResponse, IsUserAuthorisedFrResponse, IsUserAuthorisedFrRequest, PizzaListFrRequest, AbstractPizzaListFrItem}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class MainController @Inject() (cc: ControllerComponents)(
  implicit
  exec:         ExecutionContext,
  system:       ActorSystem,
  mat:          Materializer,
  assetsFinder: AssetsFinder
)
  extends AbstractController(cc) {

  implicit val askTimeout: Timeout = FrontendSettings.askTimeOutSeconds.seconds

  val processingdActor = system.actorSelection("/user/processing-actor")
  val authorisationdActor = system.actorSelection("/user/authorisation-actor")

  def listPizzas = Action.async { request ⇒

    val session = request.session
    val refId = session.get(FrontendSettings.REFERENCE_USER_KEY)

    (authorisationdActor ? IsUserAuthorisedFrRequest(refId)).mapTo[IsUserAuthorisedFrResponse].flatMap {

      case resp: IsUserAuthorisedFrResponse ⇒

        if (resp.loginSession.refId.nonEmpty && resp.loginSession.token.nonEmpty) {
          (processingdActor ? PizzaListFrRequest()).mapTo[PizzaListFrResponse]
            .map { response ⇒
              Ok(views.html.pizza_catalog(
                pizzas = response.items.getOrElse(Vector.empty[AbstractPizzaListFrItem])
              )(assetsFinder))
            }
        } else {
          Future.successful(Ok(views.html.need_to_login(assetsFinder)))
        }

      case _ ⇒
        Future.successful(Ok(views.html.internal_error("Unknow answer from authorise service")(assetsFinder)))

    }

  }

}
