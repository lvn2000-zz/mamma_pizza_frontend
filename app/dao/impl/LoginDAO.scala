package dao.impl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.slf4j.LoggerFactory
import models.LoginFrSession
import dao.DAOFactory


class LoginDAO extends DAOFactory[LoginFrSession] {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def findByRefId(refId: String): Future[Option[LoginFrSession]] = {
    super.listAll.map(_.find(_.refId == refId))
  }

  def findByToken(token: String): Future[Option[LoginFrSession]] = {
    super.listAll.map(_.filter(_.token.nonEmpty).find(_.token.get == token))
  }

}

object LoginDAO {
  def apply() = new LoginDAO()
}
