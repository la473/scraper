package http.scrap.dao

import cats.effect.IO
import http.scrap.dao.Tables.ScrapResultTable
import http.scrap.entities.ScrapResult
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

trait ScrapResultDao {
  def insert(scrapRequest: ScrapResult): IO[ScrapResult]

  def insertAll(results: Seq[ScrapResult]): IO[Seq[ScrapResult]]

  def findAll(from: Option[Long], to: Option[Long]): IO[Seq[ScrapResult]]
}

class ScrapResultDaoImp(
  db: Database,
  scrapResults: TableQuery[ScrapResultTable]
) extends ScrapResultDao {

  override def insert(scrap: ScrapResult): IO[ScrapResult] = {
    IO.fromFuture(IO(db.run(scrapResults += scrap))).map(_ => scrap)
  }

  override def insertAll(results: Seq[ScrapResult]): IO[Seq[ScrapResult]] = {
    results match {
      case Nil =>
        IO.pure(Nil)
      case _ =>
        IO.fromFuture(IO(db.run(scrapResults ++= results))).map(_ => results)
    }
  }

  override def findAll(maybeFrom: Option[Long], maybeTo: Option[Long]): IO[Seq[ScrapResult]] = {
    IO.fromFuture(IO(db.run[Seq[ScrapResult]](
      (maybeFrom, maybeTo) match {
        case (Some(from), Some(to)) =>
          scrapResults.filter(e => e.createdAt >= from && e.createdAt <= to).result
        case _ =>
          scrapResults.result
      }
    )))
  }
}