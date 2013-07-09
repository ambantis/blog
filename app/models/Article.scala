package models

import org.joda.time.LocalDate
import play.api.libs.json.{Format, Json}
import play.Logger

/**
 *
 * User: Alexandros Bantis
 * Date: 7/6/13
 * Time: 2:41 PM
 */
case class Article(slug: String, title: String, posted: LocalDate, body: String,
                   tags: List[String], comments: List[Comment])
  // todo:2013-07-06:ambantis:change blog content type from String to more appropriate type

case class Comment(_id: String, username: String, posted: LocalDate, body: String)


object Article {

  val articles = List(
    Article("hello-world", "Hello World!", new LocalDate("2013-06-13"), "<p>Hello <em>World</em>!</p>",
      List("hack"), Nil),
    Article("athena-je-t-aime", "Athena, I love you!", new LocalDate("2013-06-14"),
      "<p><strong>Athena</strong> is amazing<p>", List("home"), Nil)
  )

  def getId(post: Article): String = {
    post.posted.getYear + "/" + post.posted.getMonthOfYear + "/" + post.slug
  }

  def findArticle(id: String): Option[Article] = articles.find(p => getId(p) == id)

  def findArticleByYear(year: Int): List[Article] =
    articles.filter(_.posted.getYear == year)

  def findArticlesByMonth(year: Int, month: Int): List[Article] =
    articles.filter(p =>
      p.posted.getYear == year &&
      p.posted.getMonthOfYear == month)

  implicit val commentFormat = Format(Json.reads[Comment], Json.writes[Comment])
  implicit val articleFormat = Format(Json.reads[Article], Json.writes[Article])
}

