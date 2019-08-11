//package app.actors
//
//import scala.concurrent.Await
//import scala.concurrent.duration.Duration
//
//import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
//import org.slf4j.LoggerFactory
//
//import actors.{AuthorisationFrActor, ProcessingFrActor}
//import akka.testkit.TestKit
//import app.{ActorSystemTest, TestInit}
//import models.{IsUserAuthorisedFrRequest, IsUserAuthorisedFrResponse, LoginFrSession}
//import akka.pattern._
//
//class AuthorisationFrActorExec extends FlatSpec with Matchers with BeforeAndAfterAll with TestInit {
//
//  private val logger = LoggerFactory.getLogger(this.getClass)
//
//  val systemTest = new ActorSystemTest()
//  val system = systemTest.system
//
//  val authorisationActor = system.actorOf(AuthorisationFrActor.props(system), "authorisation-actor")
//  val processingActor = system.actorOf(ProcessingFrActor.props(system), "processing-actor")
//  
//
//  override def beforeAll() = {
//  }
//
//  override def afterAll() = {
//    TestKit.shutdownActorSystem(system)
//    systemTest.shutdown()
//  }
//
//  "AuthorisationFrActor on IsUserAuthorisedFrRequest" should
//    "should response with IsUserAuthorisedFrResponse" in {
//
//      val result = Await.result(authorisationActor ? IsUserAuthorisedFrRequest(), Duration.Inf)
//      assert(result.isInstanceOf[IsUserAuthorisedFrResponse])
//
//    }
//
//  "AuthorisationFrActor" should
//    "should store refId" in {
//
//      val result = Await.result(authorisationActor ? IsUserAuthorisedFrRequest(), Duration.Inf)
//      val lgs: LoginFrSession = result.asInstanceOf[IsUserAuthorisedFrResponse].loginSession
//
//      val lgSRefId = lgs.refId
//      val lgSesionId = lgs.logicSessionId
//
//      val result1 = Await.result(authorisationActor ? IsUserAuthorisedFrRequest(Some(lgSRefId)), Duration.Inf)
//      val lgs1: LoginFrSession = result1.asInstanceOf[IsUserAuthorisedFrResponse].loginSession
//
//      assert(lgs1.refId == lgSRefId && lgs1.logicSessionId == lgSesionId)
//
//    }
//
//}