package scheduller

import cats.effect.IO
import http.scrap.entities.{ScrapRequest, ScrapResult}
import org.jsoup.Jsoup

trait Scrapper {
  def scrap(request: ScrapRequest): IO[ScrapResult]
}

class JsoupScrapper extends Scrapper {
  override def scrap(request: ScrapRequest): IO[ScrapResult] = {
    IO.blocking {
      val document = Jsoup.connect(request.url).userAgent("Mozilla/17.0").get
      val question = document.select(".a-offscreen").html
      ScrapResult(scrapRequestId = request.id, price = question)
    }
  }
}
