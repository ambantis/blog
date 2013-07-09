package controllers

import play.api.mvc._
import play.api.libs.json.Json.toJson
import models.Article
import play.Logger

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listYear(year: Int) = Action {
    val articles = Article.findArticleByYear(year)
    Ok("hello")
//    Ok(toJson(articles))
  }

  def listMonth(year: Int, month: Int) = Action {
    val articles = Article.findArticlesByMonth(year, month)
    Ok(toJson(articles))
  }

  def findArticle(year: Int, month: Int, slug: String) = Action {
    val key = year + "/" + month + "/" + slug
    Logger.debug("key = " + key)
    val article = Article.findArticle(key)
    article match {
      case Some(post) => Ok(toJson(post))
      case None       => NotFound
    }
  }
}