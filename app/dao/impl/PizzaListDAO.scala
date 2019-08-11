package dao.impl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import org.slf4j.LoggerFactory

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.util.Timeout.durationToTimeout
import dao.DAOFactory
import javax.inject.Inject
import models.{AbstractPizzaListFrItem, PizzaListFrRequest, PizzaListFrResponse}
import utils.FrontendSettings
import utils.OtherFrHelper
import akka.pattern._
import com.pizzeria.common.utils.CommonUtils


class PizzaListDAO @Inject() (system: ActorSystem) extends DAOFactory[AbstractPizzaListFrItem] {

  private val logger = LoggerFactory.getLogger(this.getClass)

  implicit val askTimeout: Timeout = FrontendSettings.askTimeOutSeconds.seconds

  val jmsRequestorActor = system.actorSelection("/user/jms-requestor-actor")
  val processingActor = system.actorSelection("/user/processing-actor")

  val otherHelper = OtherFrHelper()

  def updateOrInsert(needUpdateItems: Vector[AbstractPizzaListFrItem]): Future[Vector[Boolean]] = {

    super.listAll.flatMap(listAll ⇒
      CommonUtils().serialiseFutures(needUpdateItems.map(item ⇒
        listAll.find(_.id == item.id) match {
          case Some(foundItem) ⇒ super.update(item, foundItem)
          case _               ⇒ super.add(item)
        })))

  }

  def refreshCatalog(): Future[PizzaListFrResponse] = {

    logger.debug("Requesting fresh pizza list from backend..")
    (jmsRequestorActor ? PizzaListFrRequest())
      .flatMap(_ ⇒ processingActor ? PizzaListFrRequest()).mapTo[PizzaListFrResponse]

  }

}

object PizzaListDAO {
  def apply(system: ActorSystem) = new PizzaListDAO(system)
}
