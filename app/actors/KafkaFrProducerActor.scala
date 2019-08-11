package actors

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import org.apache.kafka.clients.producer.ProducerRecord

import com.pizzeria.common.api.PizzaMessage
import com.pizzeria.common.utils.CommonUtils

import akka.{Done, NotUsed}
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import akka.util.Timeout
import javax.inject.Inject
import utils.PrettyPrint
import akka.pattern._

class KafkaFrProducerActor @Inject() (system: ActorSystem, materializer: Materializer)(producerSettings: ProducerSettings[String, Array[Byte]], topic: String) extends Actor with ActorLogging {

  private implicit val executionContext = context.system.dispatcher
  private implicit val askTimeout: Timeout = 60.seconds

  implicit val mat = materializer

  def receive = {

    case pMsg: PizzaMessage ⇒
      log.info(s"Sending message ${PrettyPrint.prettyPrint(pMsg)} to kafka server")
      sendMessage(Vector(pMsg)) pipeTo sender()

    case _ ⇒ Actor.emptyBehavior
  }

  private def sendMessage(pzMessages: Vector[PizzaMessage]): Future[Done] = {

    val src: Source[ProducerRecord[String, Array[Byte]], NotUsed] = Source[PizzaMessage](pzMessages)
      .map(CommonUtils().serlzVal(_))
      .filter(_.nonEmpty)
      .map(optV ⇒ new ProducerRecord[String, Array[Byte]](topic, optV.get))

    src.runWith(Producer.plainSink(producerSettings))
  }

}

object KafkaFrProducerActor {
  def props(system: ActorSystem, materializer: Materializer)(producerSettings: ProducerSettings[String, Array[Byte]], topic: String) = Props(classOf[KafkaFrProducerActor], system: ActorSystem, materializer: Materializer, producerSettings, topic)

}