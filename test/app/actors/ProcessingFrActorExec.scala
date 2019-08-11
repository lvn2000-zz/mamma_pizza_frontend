//package app.actors
//
//import scala.concurrent.Await
//import scala.concurrent.duration.Duration
//
//import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
//import org.slf4j.LoggerFactory
//
//import akka.testkit.TestKit
//import app.{ActorSystemTest, TestInit}
//import models.{AuthFrRequest, AuthFrResponse}
//import akka.pattern._
//import services.MyInit
//import models.AuthGuestFrRequest
//
//class ProcessingFrActorExec extends FlatSpec with Matchers with BeforeAndAfterAll with TestInit {
//
//  private val logger = LoggerFactory.getLogger(this.getClass)
//
//  val systemTest = new ActorSystemTest()
//  implicit val system = systemTest.system
//
//  val myInit = new MyInit()
//  val processingActor = system.actorSelection("/user/processing-actor")
//
//  override def beforeAll() = {
//    Thread.sleep(3000)
//  }
//
//  override def afterAll() = {
//    TestKit.shutdownActorSystem(system)
//    systemTest.shutdown()
//  }
//
//  "ProcessinFrActor on AuthFrRequest" should
//    "should response with AuthFrResponse" in {
//
//      val authReqCC = AuthFrRequest(
//        userName = Some("user"),
//        password = Some("password"),
//        refId = Some("oireqwoiueuoiwq&!lkj"))
//
//      val authGuestReqCC = AuthGuestFrRequest(refId = Some("xxx"))
//
//      val result = Await.result(processingActor ? authGuestReqCC, Duration.Inf)
//      assert(result.isInstanceOf[AuthFrResponse])
//
//    }
//
//}