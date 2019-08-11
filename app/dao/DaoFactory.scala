package dao

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait DAOFactory[T] {

  private val data = collection.mutable.ListBuffer.empty[T]

  def add(item: T)(implicit context: ExecutionContext): Future[Boolean] = {
    Future.successful {
      data += item
      data.contains(item)
    }
  }

  def add(items: Vector[T])(implicit context: ExecutionContext): Future[Vector[Boolean]] = {
    Future.successful {
      data ++= items
      items.map(i ⇒ data.contains(i))
    }
  }

  def delete(item: T)(implicit context: ExecutionContext): Future[Boolean] = {
    Future.successful {
      Try {
        data -= item
        !data.contains(item)
      } match {
        case Success(i) ⇒ i
        case Failure(_) ⇒ false
      }
    }
  }

  def delete(items: Vector[T])(implicit context: ExecutionContext): Future[Vector[Boolean]] = {
    Future.successful {
      Try {
        data --= items
        items.map(i ⇒ !data.contains(i))
      } match {
        case Success(d) ⇒ d
        case Failure(_) ⇒ items.map(_ ⇒ false)
      }
    }
  }

  def getByCode(code: String)(implicit context: ExecutionContext): Future[Option[T]] = {
    Future.successful(None) // just stub
  }

  def update(itemOld: T, itemNew: T)(implicit context: ExecutionContext): Future[Boolean] = {
    Future.successful {
      Try{
        val idx = data.indexOf(itemOld)
        data.update(idx, itemNew)
      } match {
        case Success(_) ⇒ true
        case Failure(_) ⇒ false
      }
    }
  }

  def listAll(implicit context: ExecutionContext): Future[Vector[T]] = {
    Future.successful(data.toVector)
  }

  def empty(implicit context: ExecutionContext): Future[Boolean] = {
    Future{
      data.clear()
      data.isEmpty
    }
  }

}
