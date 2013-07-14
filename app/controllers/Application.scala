package controllers

import play.api.mvc.{Controller,Action}

/**
 *
 * User: Alexandros Bantis
 * Date: 7/11/13
 * Time: 11:08 PM
 */
object Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index("Welcome"))
  }
}
