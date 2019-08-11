package utils


import com.typesafe.config.ConfigFactory
import play.api.Configuration


/*
 * This object used for reading settings from /conf/application.conf file
 */

object FrontendSettings {

  //constants
  val GUEST_KEY = "guest"
  
  //KEYS in config
  val ASK_TIMEOUT_KEY = "ask_time_out"

  lazy val REFERENCE_USER_KEY = "pizza_ref_id"
  
  lazy val jsonCoding = "UTF-8"

  private val config = Configuration(ConfigFactory.load())

  //TODO need to fix read settings functionality from application
  lazy val askTimeOutSeconds = config.getOptional[Int](ASK_TIMEOUT_KEY).getOrElse(1200)
  
  val base_topic = config.get[String]("topics.base_topic")
  val request_topic = config.get[String]("topics.request_topic")
  val response_topic = config.get[String]("topics.response_topic")

  val item_topic = config.get[String]("topics.item_topic")
  val implementation_topic = config.get[String]("topics.implementation_topic")
  val property_topic = config.get[String]("topics.property_topic")
  val history_topic = config.get[String]("topics.history_topic")



  
  
}
