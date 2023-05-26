package http.scrap.dao

import com.softwaremill.macwire.wire
import slick.jdbc.H2Profile.api._

trait DatabaseDependencies {

  db.run(DBIO.seq(
    Tables.schema.createIfNotExists
  ))
  private implicit lazy val db: Database = Database.forConfig("h2mem")

  lazy val scrapRequests = Tables.scrapRequests
  lazy val scrapRequestDao: ScrapRequestDao = wire[ScrapRequestDaoImp]
  lazy val scrapResults = Tables.scrapResults
  lazy val scrapResultDao: ScrapResultDao = wire[ScrapResultDaoImp]
}
