package scheduller

import cats.effect.{ExitCode, IO}
import fs2.Stream

import scala.concurrent.duration.DurationInt

object Scheduler {

  lazy val di: SchedulerDependencies = new SchedulerDependencies {}

  def run(): IO[Unit] ={
    Stream.awakeEvery[IO](di.scrapPeriod.seconds)
      .evalMap{_ =>
        di.scrappingService.scrap()
      }
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
