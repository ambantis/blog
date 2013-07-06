package models

import org.joda.time.LocalDate

/**
 *
 * User: Alexandros Bantis
 * Date: 7/6/13
 * Time: 2:41 PM
 */
case class Article(slug: String, title: String, posted: LocalDate, body: String, comments: List[Comment])
  // todo:2013-07-06:ambantis:change blog content type from String to more appropriate type

case class Comment(_id: String, username: String, posted: LocalDate, body: String)

object Article {

  val articles = List(
    Article("hello-world", "Hello World!", new LocalDate("2013-06-13"), "Hello World!", Nil),
    Article("athena-je-t'aime", "Athena, Je t'aime!", new LocalDate("2013-06-14"), "Athena is amazing", Nil)
  )

  def getId(post: Article): String = {
    post.posted.getYear + '/' + post.posted.getMonthOfYear + '/' + post.slug
  }

  def getArticle(_id: String): Option[Article] = articles.find(p => getId(p) == _id)

  def getArticlesOfYear(year: String): List[Article] =
    articles.filter(_.posted.getYear.toString == year)

  def getArticlesOfYearMonth(year: String, month: String): List[Article] =
    articles.filter(p =>
      p.posted.getYear.toString == year &&
      p.posted.getMonthOfYear.toString == month)
}
