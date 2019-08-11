//package app.actors
//
//import java.util.Properties
//import scala.concurrent.Await
//import scala.concurrent.duration.Duration
//import org.pizza.messages.AbstractEvent
//import org.pizza.messages.impl.{PizzaCatalogRequest, PizzaCatalogResponse}
//import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
//import org.slf4j.LoggerFactory
//import com.common.util.PrettyPrint
//import akka.stream.ActorMaterializer
//import akka.testkit.TestKit
//import app.{ActorSystemTest, TestInit}
//import javax.jms.{BytesMessage, Connection, ConnectionFactory, DeliveryMode, Destination, Message, MessageConsumer, MessageListener, MessageProducer, Queue, Session}
//import javax.naming.{Context, InitialContext}
//import models.{AbstractPizzaCatalogFrItem, PizzaListFrRequest, PizzaListFrResponse}
//import utils.{JmsFrHelper}
//import services.MyInit
//import akka.pattern._
//
//class JMSRequestorFrActorExec extends FlatSpec with BeforeAndAfterAll with Matchers with TestInit with MessageListener {
//
//  private val logger = LoggerFactory.getLogger(this.getClass)
//
//  var testResponse: Option[AbstractEvent] = None
//
//  val ackMode = Session.AUTO_ACKNOWLEDGE
//
//  val env: Properties = new Properties()
//  env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory")
//  env.put(Context.PROVIDER_URL, messageQueueUrl)
//  env.put(Context.SECURITY_PRINCIPAL, username)
//  env.put(Context.SECURITY_CREDENTIALS, password)
//  val namingContext = new InitialContext(env)
//  val testConnectionFactory: ConnectionFactory = (namingContext.lookup(CONNECTION_FACTORY)).asInstanceOf[ConnectionFactory]
//
//  implicit val testConnection: Connection = testConnectionFactory.createConnection(
//    env.getProperty(Context.SECURITY_PRINCIPAL),
//    env.getProperty(Context.SECURITY_CREDENTIALS)
//  )
//
//  implicit val testSession: Session = testConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)
//  val queueRequestMsg: Queue = testSession.createQueue(testRequestQueueName)
//
//  val destinationSendTo: Destination = testSession.createQueue(testRequestQueueName)
//  val destinationReplyTo: Destination = testSession.createQueue(testResponseQueueName)
//
//  val producer: MessageProducer = testSession.createProducer(destinationSendTo)
//  producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)
//
//  val queueResponseMsg: Queue = testSession.createQueue(testResponseQueueName)
//  val consumer: MessageConsumer = testSession.createConsumer(queueResponseMsg)
//  consumer.setMessageListener(this)
//
//  val jmsHelper = JmsFrHelper()
//
//  val systemTest = new ActorSystemTest()
//  implicit val system = systemTest.system
//  implicit val dispatcher = system.dispatcher
//
//  val myInit = new MyInit()
//
//  implicit val mat = ActorMaterializer()
//
//  val jmsRequestorActor = system.actorSelection("/user/jms-requestor-actor")
//
//  val processingActor = system.actorSelection("/user/processing-actor")
//
//  override def beforeAll() = {
//    testConnection.start()
//  }
//
//  override def afterAll() = {
//    consumer.close()
//    testSession.close()
//    testConnection.close()
//
//    TestKit.shutdownActorSystem(system)
//  }
//
//  override def onMessage(message: Message): Unit = {
//    //    val textMsg = getTextRequest(message)
//    //    testResponse = Some(read(textMsg))
//
//    testResponse = message match {
//      case msg: BytesMessage ⇒ jmsHelper.extractObject(msg)
//      case _                 ⇒ None
//    }
//  }
//
//  def doSendMsg(msg: AbstractEvent): Unit = {
//
//    //message for sending
//    jmsHelper.createMessage(msg, testSession).map(messageToSend ⇒ {
//      producer.send(messageToSend)
//      messageToSend.acknowledge()
//      logger.info(s"Message: ${PrettyPrint.prettyPrint(messageToSend)} were sent successfully.")
//    })
//  }
//
//  "JMSRequestor " should
//    "responds on requests with real data pizza list using JMS directly" in {
//
//      val msgRequest = new PizzaCatalogRequest()
//      doSendMsg(msgRequest)
//      Thread.sleep(5000L)
//      val result = testResponse
//      assert(result.nonEmpty && result.get.isInstanceOf[PizzaCatalogResponse])
//    }
//
//  "JMSRequestor " should
//    "responds on requests with real data pizza list via Processing actor" in {
//
//      //clear data in dao
//      processingActor ! PizzaListFrResponse(items = Some(Vector.empty[AbstractPizzaCatalogFrItem]))
//      Thread.sleep(1000)
//
//      //request data from backend
//      jmsRequestorActor ! PizzaListFrRequest()
//      Thread.sleep(3000)
//
//      val result = Await.result((processingActor ? PizzaListFrRequest()).mapTo[PizzaListFrResponse], timeToWait)
//      val lg = s"List of pizzas in list: ${result.items.mkString}"
//      logger.debug(lg)
//      assert(result.items.nonEmpty)
//
//    }
//
//}
