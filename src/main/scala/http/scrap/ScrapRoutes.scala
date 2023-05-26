package http.scrap

import cats.effect.IO
import http.scrap.entities.ScrapRequestCreate
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

class ScrapRoutes(scrapService: ScrapService) {

  object FromParam extends OptionalQueryParamDecoderMatcher[Long]("from")
  object ToParam extends OptionalQueryParamDecoderMatcher[Long]("to")

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req@POST -> Root / "scraps" =>
      req
        .as[ScrapRequestCreate]
        .flatMap { scrapRequest =>
          scrapService
            .add(scrapRequest)
            .flatMap(scrapRequest => Ok(scrapRequest.id))
        }

    case GET -> Root / "scraps" =>
      scrapService
        .findAllRequests()
        .flatMap(Ok(_))

    case DELETE -> Root / "scraps" / id =>
      scrapService
        .delete(id)
        .flatMap {
          case Some(_) => Ok()
          case None => NotFound("Scrap not found")
        }

    case GET -> Root / "scraps" / "results" :? FromParam(maybeFrom) :? ToParam(maybeTo) =>
      scrapService
        .findAllResults(maybeFrom, maybeTo)
        .flatMap(Ok(_))
  }
}
