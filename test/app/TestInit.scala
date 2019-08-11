
package app

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration

import akka.util.Timeout
//import models.{PizzaCatalogFrItem, ShortCatalogFrItem}
import models.AuthFrRequest
import models.AuthFrResponse

trait TestInit {

  implicit val timeToWait = Duration(10000, TimeUnit.SECONDS)
  implicit val timeout = Timeout(10000, TimeUnit.SECONDS)

  val CONNECTION_FACTORY = "jms/RemoteConnectionFactory"
  val username = "remoteJmsUser"
  val password = "123456"

  val testRequestQueueName = "portalBackendInQueue"
  val testResponseQueueName = "portalGatewayInQueue" //"jms/queue/portalGatewayInQueue"

  val jmsHost = "127.0.0.1"
  val jmsPort = "8080"
  val messageQueueUrl = s"http-remoting://$jmsHost:$jmsPort" //s"vm://localhost:8080?broker.persistent=false"

  //val network = false
  //    if (network) {
  //      val port = SocketUtil.temporaryServerAddress(host).getPort
  //      val serverUrl = s"tcp://$host:$port"
  //      serverUrl
  //    } else {
  //      s"vm://$host?broker.persistent=false"
  //    }

//  val catalogFixture = Vector[PizzaCatalogFrItem](
//    PizzaCatalogFrItem(
//      id          = Some(1),
//      title       = Some("Margarita"),
//      ingredients = Some("sauce, cheese, oregano, fresh basil (option)"),
//      priceSizeM  = Some("13.5"),
//      priceSizeL  = Some("17"),
//      priceSizeXL = Some("20"),
//      vegan       = true,
//      meat        = false,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(2),
//      title       = Some("Funghos"),
//      ingredients = Some("sauce, cheese, mushrooms, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("19.3"),
//      priceSizeXL = Some("22.5"),
//      vegan       = true,
//      meat        = false,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(3),
//      title       = Some("Romanos"),
//      ingredients = Some("sauce, cheese, ham, oregano"),
//      priceSizeM  = Some("16.9"),
//      priceSizeL  = Some("20.5"),
//      priceSizeXL = Some("23.8"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(4),
//      title       = Some("Pollo"),
//      ingredients = Some("sauce, cheese, chicken, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("22.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(5),
//      title       = Some("Capriciosa"),
//      ingredients = Some("sauce, cheese, mushrooms, ham, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(6),
//      title       = Some("Salame"),
//      ingredients = Some("sauce, cheese, salami x 2, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      spicy       = true,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(7),
//      title       = Some("Carbonara"),
//      ingredients = Some("sauce śmietanowy, cheese, onion, boczek, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(8),
//      title       = Some("Kronos"),
//      ingredients = Some("sauce, cheese, ham, salami, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(9),
//      title       = Some("Szpinakos"),
//      ingredients = Some("sauce, cheese, chicken, ser feta, szpinak, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(10),
//      title       = Some("Vesuvius"),
//      ingredients = Some("sauce, cheese, onion, bacon, papryka jalapeno, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(11),
//      title       = Some("Spartakus"),
//      ingredients = Some("sauce, cheese, tomato, olives, ser feta, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      vegan       = true,
//      meat        = false,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(12),
//      title       = Some("Quatro fromaggi"),
//      ingredients = Some("sauce, cheese, mozzarella, ser feta, ser pleśniowy, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      vegan       = true,
//      meat        = false,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(13),
//      title       = Some("Farmeros"),
//      ingredients = Some("sauce, cheese, onion, ham, fresh tomato, sweetcorn, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(14),
//      title       = Some("Tuna"),
//      ingredients = Some("sauce, cheese, mozzarella, ser feta, ser pleśniowy, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      vegan       = true,
//      meat        = false,
//      fish        = true,
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(15),
//      title       = Some("Cantana"),
//      ingredients = Some("sauce, cheese, mushrooms, chicken, brokuły, garlic, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    ),
//    PizzaCatalogFrItem(
//      id          = Some(16),
//      title       = Some("Cortez"),
//      ingredients = Some("sauce, cheese, onion, mięso wieprzowe, czerwona fasola, sweetcorn, papryka jalapeno, oregano"),
//      priceSizeM  = Some("15"),
//      priceSizeL  = Some("20.2"),
//      priceSizeXL = Some("23.2"),
//      catType     = Some("pizza")
//    )
//  )
//
//  val simpleCatalogFixture = Vector[ShortCatalogFrItem](
//    ShortCatalogFrItem(
//      id      = Some(1),
//      title   = Some("Margarita"),
//      price   = Some("13.5"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(2),
//      title   = Some("Funghos"),
//      price   = Some("22.5"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(3),
//      title   = Some("Romanos"),
//      price   = Some("16.9"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(4),
//      title   = Some("Pollo"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(5),
//      title   = Some("Capriciosa"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(6),
//      title   = Some("Salame"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(7),
//      title   = Some("Carbonara"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(8),
//      title   = Some("Kronos"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(9),
//      title   = Some("Szpinakos"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(10),
//      title   = Some("Vesuvius"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(11),
//      title   = Some("Spartakus"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(12),
//      title   = Some("Quatro fromaggi"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(13),
//      title   = Some("Farmeros"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(14),
//      title   = Some("Tuna"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(15),
//      title   = Some("Cantana"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    ),
//    ShortCatalogFrItem(
//      id      = Some(16),
//      title   = Some("Cortez"),
//      price   = Some("15"),
//      catType = Some("pizza")
//    )
//  )
  
  val AuthFrRequestFixture = AuthFrRequest()
  val AuthFrResponseFixture = AuthFrResponse()

}