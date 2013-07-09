package controllers

import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor
import play.api.libs.json.{JsArray, JsValue, Json, JsObject}
import scala.concurrent.Future

/**
 *
 *
 *
 *
 */



/**
 *
 * User: Alexandros Bantis
 * Date: 7/8/13
 * Time: 7:44 PM
 */
object Blog extends Controller with MongoController {

//  dbSchema = Json.obj(
//    "articles" -> Json.arr(
//      Json.obj(
//        "date" -> Json.obj(
//          "year" -> 2013,
//          "month" -> 6,
//          "day" -> 20
//        ),
//        "uri" -> "2013/06/hello-world",
//        "slug" -> "hello-world",
//        "title" -> "Hello World!",
//         "body" -> "<p>Hello <em>World</em></p>",
//         "tags" -> Json.arr("hack"),
//         "comments" -> Json.arr(
//            Json.obj(
//              "user" -> "jim",
//              "posted" -> "2013-06-30",
//              "comment" -> "way to go!"
//            )
//         )
//      )
//    )
//  )

  def articles: JSONCollection = db.collection[JSONCollection]("articles")

  def listYear(year: Int) = Action { implicit request =>
    Async {
      val cursor: Cursor[JsObject] = articles.
        find(Json.obj("date.year" -> year)).
        projection(Json.obj("_id" -> 0)).
        sort(Json.obj("posted" -> 1)).
        cursor[JsObject]

      val futureResult: Future[List[JsValue]] = cursor.toList()
      futureResult map(a => Ok(Json.prettyPrint(JsArray(a))))
    }
  }
}
