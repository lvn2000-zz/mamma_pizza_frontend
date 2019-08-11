package actors

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Random

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.util.Timeout
import dao.impl.LoginDAO
import javax.inject.Inject
import models.{AuthFrRequest, AuthFrResponse, IsUserAuthorisedFrRequest, IsUserAuthorisedFrResponse, LoginFrSession, UserFr}
import utils.FrontendSettings
import akka.pattern._

class AuthorisationFrActor @Inject() (system: ActorSystem) extends Actor with ActorLogging {

  implicit val askTimeout: Timeout = FrontendSettings.askTimeOutSeconds.seconds
  implicit val cntx = system.dispatcher

  val loginDAO = LoginDAO()

  val processing = system.actorSelection("/user/processing-actor")

  def receive = {

    case req: IsUserAuthorisedFrRequest ⇒

      (if (req.refId.isEmpty) {

        newLogicSessionForRefSessionIdUser().map(newLSession ⇒ {
          loginDAO.add(newLSession)
          IsUserAuthorisedFrResponse (newLSession)
        })

      } else {

        val rfId = req.refId.get

        loginDAO.findByRefId(rfId).map(ls ⇒
          ls match {
            case Some(lSession) ⇒ IsUserAuthorisedFrResponse(lSession)
            case None ⇒
              newLogicSessionForRefSessionIdUser(refId = rfId).map(newLSession ⇒ {
                loginDAO.add(newLSession)
                IsUserAuthorisedFrResponse(newLSession)
              })
          })
      }) pipeTo sender

    case _ ⇒
  }

  private def generateUUID(): String = {
    Array.range(1, 20).foldLeft("") ((_, _) ⇒ Random.nextPrintableChar().toString())
  }

  private def requestCredentialsForUser(refId: String): Future[AuthFrResponse] = {
    (processing ? AuthFrRequest(Some(refId))).mapTo[AuthFrResponse]
  }

  def newLogicSessionForRefSessionIdUser(
    refId:     String = generateUUID(),
    sessionid: String = generateUUID(),
    user:      UserFr = UserFr()
  ): Future[LoginFrSession] = {

    for {
      tk ← requestCredentialsForUser(refId).map(_.token)
      ls ← {
        val token = tk
        Future.successful{
          LoginFrSession(
            logicSessionId = sessionid,
            refId          = refId,
            user           = user,
            token          = token
          )
        }
      }
    } yield ls

  }

}

object AuthorisationFrActor {
  def props(system: ActorSystem) = Props(new AuthorisationFrActor(system))
}
