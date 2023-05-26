package http

import cats.effect.{ExitCode, IO}
import org.http4s.{HttpApp, _}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._

import scala.concurrent.ExecutionContext

object ApiApplication {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  lazy val di: ApiDependencies = new ApiDependencies {}

  def routes: HttpRoutes[IO] = di.productRoutes.routes
  def httpApp: HttpApp[IO] = routes.orNotFound

  def run() =
    BlazeServerBuilder[IO]
      .withExecutionContext(ec)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)
      .resource
      .useForever
      .as(ExitCode.Success)
}
