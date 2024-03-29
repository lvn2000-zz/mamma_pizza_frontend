package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("")(assetsFinder))
  }

  //  def wsJs = Action{
  //     return ok(views.js.helper.js.render());
  //  }
  //
  //      // Websocket interface
  //  val wsInterface:WebSocket[String] = {
  //         new WebSocket() {
  //
  //            // called when websocket handshake is done
  //            def onReady(inOut:WebSocket.json(in, out)){
  //                //SimpleChat.start(in, out);
  //            }
  //        };
  //    }

}
