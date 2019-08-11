package services

import actors.{AuthorisationFrActor, KafkaFrConsumerActor, KafkaFrProducerActor, ProcessingFrActor}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import dao.impl.PizzaListDAO
import javax.inject.{Inject, Singleton}
import utils.FrontendSettings
import akka.stream.Materializer
import akka.kafka.ConsumerSettings
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import akka.kafka.ProducerSettings
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

/*
 * Init class of application. Here will initialize internal values and execute service actors (JMSConsumer, for example)
 */

@Singleton
class MyInit @Inject() (implicit system: ActorSystem) {

  implicit val mat = ActorMaterializer()

  val authorisationActor = system.actorOf(AuthorisationFrActor.props(system), "authorisation-actor")

  val producerSettings =
    ProducerSettings(system, new StringSerializer, new ByteArraySerializer)
      .withBootstrapServers("localhost:9092")

  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new ByteArrayDeserializer)
    .withBootstrapServers("localhost:9092")
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  
  
  val kafkaConsumerRequestTopic = system.actorOf(KafkaFrConsumerActor.props(system, mat)(consumerSettings, FrontendSettings.request_topic), "kafka-consumer-request-actor")
  val kafkaProducerResponseTopic = system.actorOf(KafkaFrProducerActor.props(system, mat)(producerSettings, FrontendSettings.response_topic), "kafka-producer-response-actor")

  val processingActor = system.actorOf(ProcessingFrActor.props(system), "processing-actor")

  val pizzaCatalogDAO = PizzaListDAO(system)

  pizzaCatalogDAO.refreshCatalog()

}
