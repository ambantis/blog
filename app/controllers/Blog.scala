package controllers

import play.api.libs.json.{JsObject, JsValue, Json, JsArray}
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.json.BSONFormats.toJSON
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.MongoController
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument
import reactivemongo.core.commands.{Aggregate, Project, Unwind}
import scala.concurrent.Future

/**
 *
 * User: Alexandros Bantis
 * Date: 7/8/13
 * Time: 7:44 PM
 */
object Blog extends Controller with MongoController {

  def articles: JSONCollection = db.collection[JSONCollection]("articles")

  def index = Action { implicit request =>
    Async{
      val cursor: Cursor[JsObject] = articles.
        find(Json.obj()).
        sort(Json.obj("date" -> 1)).
        cursor[JsObject]

      val futureResult: Future[List[JsValue]] = cursor.toList()
      futureResult map(a => Ok(JsArray(a)))
    }
  }

  def listYear(year: Int) = Action { implicit request =>
    Async {
      val cursor: Cursor[JsObject] = articles.
        find(Json.obj("date.year" -> year)).
        //projection(Json.obj("_id" -> 0)).
        //sort(Json.obj("posted" -> 1)).
        cursor[JsObject]

      val futureResult: Future[List[JsValue]] = cursor.toList()
      futureResult map(a => Ok(JsArray(a)))
    }
  }

  def listMonth(year: Int, month: Int) = Action { implicit request =>
    Async {
      val cursor: Cursor[JsObject] = articles.
        find(Json.obj("$and" -> Json.arr(Json.obj("date.year" -> year),
                                         Json.obj("date.month" -> month)))).
        projection(Json.obj("_id" -> 0)).
        cursor[JsObject]

      val futureResult: Future[List[JsValue]] = cursor.toList()
      futureResult map(a => Ok(Json.prettyPrint(JsArray(a))))
    }
  }

  def findArticle(year: Int, month: Int, slug: String) = Action { implicit request =>
    Async {
      val result: Future[Option[JsValue]] = articles.
        find(Json.obj("$and" -> Json.arr(Json.obj("date.year" -> year),
                                         Json.obj("date.month" -> month),
                                         Json.obj("slug" -> slug)))).
        projection(Json.obj("_id" -> 0)).
        cursor[JsObject].headOption()

      result.map {
        case Some(x) => Ok(Json.prettyPrint(x))
        case None => NotFound
      }
    }
  }

  def listTags = Action { implicit request =>
    Async {
      val command = Aggregate("articles", Seq(
        Project("tags" -> 1),
        Unwind("tags")
      ))
      val result: Future[Stream[BSONDocument]] = db.command(command)
      result.map(x => Ok(JsArray(x.toList.map(y => toJSON(y) \ "tags").distinct )))
    }
  }
}