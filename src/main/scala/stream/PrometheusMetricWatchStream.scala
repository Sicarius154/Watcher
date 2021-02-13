package stream
import domain.{MetricTarget, PrometheusQueryResult}
import cats.effect.{ContextShift, Async, Timer, IO, Sync}
import cats.syntax._
import config.{ApplicationMetricProcessingConfig, Config}
import fs2.Stream
import org.slf4j.{Logger, LoggerFactory}
import web.PrometheusMetricClient

import scala.concurrent.duration._

class PrometheusMetricWatchStream(
    streamConfig: ApplicationMetricProcessingConfig,
    metricClient: PrometheusMetricClient
)(implicit
    cs: ContextShift[IO],
    timer: Timer[IO],
    sync: Sync[IO],
    async: Async[IO]
) extends MetricWatchStream {
  val log: Logger =
    LoggerFactory.getLogger(PrometheusMetricWatchStream.getClass.getSimpleName)

  override def runForever(watchList: Seq[MetricTarget]): IO[Unit] =
    prometheusStream(watchList).repeat
      .metered(streamConfig.streamSleepTime.seconds)
      .compile
      .drain

  private[stream] def prometheusStream(
      watchList: Seq[MetricTarget]
  ): Stream[IO, Option[PrometheusQueryResult]] =
    Stream
      .emits(watchList)
      .covary[IO]
      .parEvalMapUnordered(streamConfig.streamParallelismMax)(processMetricTarget)
      .parEvalMapUnordered(streamConfig.streamParallelismMax)(validateQueryResult)

  private[stream] def processMetricTarget(
      query: MetricTarget
  ): IO[Either[String, PrometheusQueryResult]] = {
    log.info(s"Processing query \'${query.name}\' in stream")
    if (query.name.isEmpty) IO(Left("Invalid query name"))
    else metricClient.getMetricValue(query)
  }

  private[stream] def validateQueryResult(
      res: Either[String, PrometheusQueryResult]
  ): IO[Option[PrometheusQueryResult]] =
    IO {
      if (res.isLeft)
        res.left.map(err =>
          log.error(s"Error when validating Query Result for: $err")
        )
      res.toOption
    }
}

object PrometheusMetricWatchStream {
  def apply(config: Config, metricClient: PrometheusMetricClient)(implicit
      cs: ContextShift[IO],
      timer: Timer[IO],
      sync: Sync[IO],
      async: Async[IO]
  ): PrometheusMetricWatchStream =
    new PrometheusMetricWatchStream(
      config.applicationMetricProcessingConfig,
      metricClient
    )
}
