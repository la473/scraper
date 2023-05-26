package http.scrap.dao

import cats.effect.IO
import http.scrap.dao.Tables.ScrapRequestTable
import http.scrap.entities.{ScrapRequest, ScrapRequestCreate}
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global


trait ScrapRequestDao {
  def insert(request: ScrapRequestCreate): IO[ScrapRequest]

  def findAll(): IO[Seq[ScrapRequest]]

  def delete(requestId: String): IO[Option[ScrapRequest]]
}

class ScrapRequestDaoImp(
  db: Database,
  scrapRequests : TableQuery[ScrapRequestTable]
) extends ScrapRequestDao {

  override def insert(scrapRequest: ScrapRequestCreate): IO[ScrapRequest] = {
    val insertee = ScrapRequest(url = scrapRequest.url)
    IO.fromFuture(IO(db.run(scrapRequests += insertee))).map(_ => insertee)
  }

  override def findAll(): IO[Seq[ScrapRequest]] =
    IO.fromFuture(IO(db.run[Seq[ScrapRequest]](scrapRequests.result)))

  override def delete(id: String): IO[Option[ScrapRequest]] = {
    val query = scrapRequests.filter(_.id === id)
    val action = for {
      results <- query.result.headOption
      _ <- query.delete
    } yield results

    IO.fromFuture(IO(db.run(action)))
  }

}