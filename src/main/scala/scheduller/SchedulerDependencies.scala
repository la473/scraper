package scheduller

import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import http.ScrapDependencies
import http.scrap.dao._
import http.scrap.{ScrapRoutes, ScrapService, ScrapServiceImp}
import slick.jdbc.H2Profile.api._

trait SchedulerDependencies extends ScrapDependencies{

  lazy val config = ConfigFactory.defaultApplication()
  lazy val scrapPeriod = config.getInt("scrap.period")
  lazy val jsoupScrapper: Scrapper = wire[JsoupScrapper]
  lazy val scrappingService: ScrappingService = wire[ScrappingServiceImp]
}
