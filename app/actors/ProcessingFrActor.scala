package actors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.util.Timeout
import dao.impl.{LoginDAO, PizzaListDAO}
import javax.inject.Inject
import models.{AbstractPizzaListFrItem, AuthFrRequest, AuthFrResponse, PizzaListFrRequest, PizzaListFrResponse}
import utils.FrontendSettings
import utils.ModelFrHelper
import akka.pattern._

class ProcessingFrActor @Inject() (system: ActorSystem) extends Actor with ActorLogging {

  implicit val askTimeout: Timeout = FrontendSettings.askTimeOutSeconds.seconds

  val pizzaListDAO: PizzaListDAO = PizzaListDAO.apply(context.system)
  val modelHelper = ModelFrHelper()
  val loginDAO = LoginDAO()

  val jmsAuthoriseRequestor = system.actorSelection("/user/jms-authorise-requestor-actor")
  val jmsRequestor = system.actorSelection("/user/jms-requestor-actor")

  override def receive = {

    case _: PizzaListFrRequest ⇒
      log.debug("Received request of pizza list..")

      pizzaListDAO.listAll.mapTo[Vector[AbstractPizzaListFrItem]].map(catalogs ⇒
        PizzaListFrResponse(items = Some(catalogs))) pipeTo sender()

    case pizzaListFrResponse: PizzaListFrResponse ⇒
      val lg = s"Got items from backend ${pizzaListFrResponse.items.mkString}"
      log.debug(lg)
      pizzaListDAO.updateOrInsert(pizzaListFrResponse.items.getOrElse(Vector.empty[AbstractPizzaListFrItem]))
    //.onComplete(_ ⇒ sender() ! Done)

    //    case authGuestReq: AuthGuestFrRequest ⇒
    //     val res =  (jmsAuthoriseRequestor ? authGuestReq)
    //
    //     val res1 = res.mapTo[AuthFrResponse]
    //
    //     res1 pipeTo sender()

    //      .map {
    //          case resp: AuthFrResponse => {
    //            val lg = s"Got response from backend ${PrettyPrint.prettyPrint(resp)}"
    //            log.debug(lg)
    //            resp
    //
    //          }
    //          case d =>
    //            val lg = s"Got response from backend ${PrettyPrint.prettyPrint(d)}"
    //            log.debug(lg)
    //
    //        } pipeTo sender()

    case authReq: AuthFrRequest ⇒
      (jmsAuthoriseRequestor ? authReq)
        .mapTo[AuthFrResponse] pipeTo sender()

    //      (jmsAuthoriseRequestor ? IsUserAuthorisedFrRequest(authreq.refId))
    //        .mapTo[IsUserAuthorisedFrResponse]
    //        .map(isUsrAuthResp ⇒ {
    //          val ls = isUsrAuthResp.loginSession
    //          AuthFrResponse(
    //            refId = Some(ls.logicSessionId),
    //            isOk  = true,
    //            token = ls.token
    //          )
    //
    //        }) pipeTo sender()

    //    case authresp: AuthFrResponse ⇒ {
    //      val lg = s"Got authentification response from backend ${PrettyPrint.prettyPrint(authresp)}"
    //      log.debug(lg)

    //TODO if were got AuthFrResponse from backend then just add user in loginDAO list session

    //      authresp.refId.map(rfId =>
    //        loginDAO.findByRefId(rfId).map(lg =>{
    //          lg.map(lgs => {
    //            val tokenWrapper = new TokenWrapper()
    //            //tokenWrapper.
    //            lgs.copy(
    //            logicSessionId = lgs.logicSessionId,
    //            user = lgs.user,
    //            refId = lgs.refId,
    //            token = lgs.token
    //            )})
    //        )
    //        )
    //    }

    //      sender() ! modelHelper.convert(authresp)

    case _ ⇒
  }

}

object ProcessingFrActor {
  def props(system: ActorSystem) = Props(new ProcessingFrActor(system))
}


