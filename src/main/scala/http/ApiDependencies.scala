package http

import com.softwaremill.macwire.wire
import http.scrap.ScrapRoutes

trait ApiDependencies extends ScrapDependencies {
  lazy val productRoutes: ScrapRoutes = wire[ScrapRoutes]
}
