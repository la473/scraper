import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxParallelSequence1
import http.ApiApplication
import scheduller.Scheduler

object Main extends IOApp{

  override def run(args: List[String]): IO[ExitCode] =
    Seq(ApiApplication.run(), Scheduler.run()).parSequence.as(ExitCode.Success)
}