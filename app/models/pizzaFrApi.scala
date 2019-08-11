package models

import com.pizzeria.common.api.{PizzaMessage, PizzaRequestMessage, PizzaResponseMessage}

import utils.FrontendSettings

case class PizzaListRequest() extends PizzaRequestMessage
case class PizzaListResponse(pizzas: Vector[AbstractPizzaListFrItem]) extends PizzaResponseMessage

trait AbstractPizzaListFrItem extends PizzaMessage {
  val id: Option[Long]
  val title: Option[String]
  val catType: Option[String]
}

case class Ingredient(val id: Option[Long], val name: String) extends PizzaMessage

case class PizzaListFrItem(
  id:          Option[Long]   = None,
  title:       Option[String] = None,
  catType:     Option[String] = None,
  ingredients: Option[String] = None,
  priceSizeM:  Option[String] = None,
  priceSizeL:  Option[String] = None,
  priceSizeXL: Option[String] = None,
  spicy:       Boolean        = false,
  vegan:       Boolean        = false,
  meat:        Boolean        = true,
  fish:        Boolean        = false,
  image:       Option[String] = None
) extends AbstractPizzaListFrItem

case class ShortListFrItem(
  id:      Option[Long]   = None,
  title:   Option[String] = None,
  catType: Option[String] = None,
  price:   Option[String] = None
) extends AbstractPizzaListFrItem

case class PizzaListFrRequest(`type`: Option[String] = Some("PIZZA_LIST_REQ")) extends PizzaRequestMessage

case class PizzaListFrResponse(
  `type`: Option[String]                          = Some("PIZZA_LIST_RESP"),
  items:  Option[Vector[AbstractPizzaListFrItem]] = None
) extends PizzaResponseMessage

case class AuthFrRequest(
  `type`:   Option[String] = Some("AUTH_REQ"),
  userName: Option[String] = Some(FrontendSettings.GUEST_KEY),
  password: Option[String] = None,
  refId:    Option[String] = None
) extends PizzaRequestMessage

case class AuthFrResponse(
  `type`: Option[String] = Some("AUTH_RESP"),
  refId:  Option[String] = None,
  isOk:   Boolean        = false,
  token:  Option[String] = None,
  error:  Option[String] = None
) extends PizzaResponseMessage

case class LoginFrSession(logicSessionId: String, user: UserFr, refId: String, token: Option[String]) extends PizzaMessage

case class UserFr(
  login:     String         = FrontendSettings.GUEST_KEY,
  firstName: Option[String] = None,
  lastName:  Option[String] = None,
  phone:     Option[String] = None,
  email:     Option[String] = None,
  address:   Option[String] = None
) extends PizzaMessage

case class IsUserAuthorisedFrRequest(refId: Option[String] = None) extends PizzaRequestMessage
case class IsUserAuthorisedFrResponse(loginSession: LoginFrSession) extends PizzaResponseMessage


