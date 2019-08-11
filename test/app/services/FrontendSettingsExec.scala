//package app.services
//
//import org.scalatest.{FlatSpec, Matchers}
//import org.slf4j.LoggerFactory
//
//import app.TestInit
//import utils.FrontendSettings
//
//class FrontendSettingsExec extends FlatSpec with Matchers with TestInit {
//
//  private val logger = LoggerFactory.getLogger(this.getClass)
//
//  "FrontendSettings - timeout setting  " should
//    "responds  with timeout setting from aplication.conf" in {
//
//      val timeOut = FrontendSettings.askTimeOutSeconds
//      val lg = s"Timeout = $timeOut"
//      logger.info(lg)
//      
//      assert(timeOut.isValidInt)
//    }
//  
//  "FrontendSettings - JMS user" should
//    "responds  with jms_user settings from aplication.conf" in {
//
//      val jmsUser = FrontendSettings.jms_user
//      val lg = s"jmsUser = $jmsUser"
//      logger.info(lg)
//      
//      assert(jmsUser.isInstanceOf[String])
//    }
//  
//
//}