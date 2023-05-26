package http.scrap.dao
import http.scrap.entities.{ScrapRequest, ScrapResult}
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

object Tables {
  class ScrapRequestTable(tag: Tag) extends Table[ScrapRequest](tag, None, "ScrapRequest") {
    override def * = (id, url) <> (ScrapRequest.tupled, ScrapRequest.unapply)

    val id: Rep[String] = column[String]("scrap_request_id", O.PrimaryKey)
    val url: Rep[String] = column[String]("url")
  }
  lazy val scrapRequests = new TableQuery(tag => new ScrapRequestTable(tag))

  class ScrapResultTable(tag: Tag) extends Table[ScrapResult](tag, None, "ScrapResult") {
    override def * = (id, scrapRequestId, price, createdAt) <> (ScrapResult.tupled, ScrapResult.unapply)

    val id: Rep[String] = column[String]("scrap_result_id", O.PrimaryKey)
    val scrapRequestId: Rep[String] = column[String]("scrap_request_id")
    val price: Rep[String] = column[String]("price")
    val createdAt: Rep[Long] = column[Long]("createdAt")

    def scrapRequest = foreignKey("SCRAP_REQ_FK", scrapRequestId, scrapRequests)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.NoAction)
  }
  lazy val scrapResults = new TableQuery(tag => new ScrapResultTable(tag))

  val schema = scrapRequests.schema ++ scrapResults.schema
}
