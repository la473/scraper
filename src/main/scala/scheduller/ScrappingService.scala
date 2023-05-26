package scheduller

import cats.effect.IO
import cats.implicits.catsSyntaxParallelSequence1
import http.scrap.ScrapService

trait ScrappingService {
  def scrap(): IO[Unit]
}

class ScrappingServiceImp(
 scrapper: Scrapper,
 scrapService: ScrapService
) extends ScrappingService {
  override def scrap(): IO[Unit] = for {
    requests <- scrapService.findAllRequests()
    results <- requests.map(scrapper.scrap).parSequence
    _ <- scrapService.addAll(results)
  } yield IO.unit
}