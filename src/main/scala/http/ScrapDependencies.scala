package http

import com.softwaremill.macwire.wire
import http.scrap.dao._
import http.scrap.{ScrapService, ScrapServiceImp}

trait ScrapDependencies extends DatabaseDependencies {
  lazy val productService: ScrapService = wire[ScrapServiceImp]
}
