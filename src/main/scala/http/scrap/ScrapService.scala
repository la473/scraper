package http.scrap

import cats.effect.IO
import http.scrap.dao.{ScrapRequestDao, ScrapResultDao}
import http.scrap.entities.{ScrapRequest, ScrapRequestCreate, ScrapResult}

trait ScrapService {
  def add(scrap: ScrapRequestCreate): IO[ScrapRequest]
  def findAllRequests(): IO[Seq[ScrapRequest]]
  def delete(scrapRequestId: String): IO[Option[ScrapRequest]]

  def findAllResults(from: Option[Long], to: Option[Long]): IO[Seq[ScrapResult]]
  def addAll(results: Seq[ScrapResult]): IO[Seq[ScrapResult]]
}

case class ScrapServiceImp(
  scrapRequestDao: ScrapRequestDao,
  scrapResultDao: ScrapResultDao
) extends ScrapService {
  override def add(scrapRequest: ScrapRequestCreate): IO[ScrapRequest] =
    scrapRequestDao.insert(scrapRequest)

  override def findAllRequests(): IO[Seq[ScrapRequest]] =
    scrapRequestDao.findAll()

  override def findAllResults(from: Option[Long], to: Option[Long]): IO[Seq[ScrapResult]] =
    scrapResultDao.findAll(from, to)

  override def delete(scrapRequestId: String): IO[Option[ScrapRequest]] =
    scrapRequestDao.delete(scrapRequestId)

  override def addAll(results: Seq[ScrapResult]): IO[Seq[ScrapResult]] =
    scrapResultDao.insertAll(results)
}
