package com.christophergagne.sprayapidemo

import scala.concurrent.Future
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import akka.io.IO
import spray.can.Http
import spray.http._
import HttpMethods._

trait SprayHostLevelClient {
  private implicit val timeout: Timeout = 5.seconds

  def sprayHostLevelClient(host: String)(implicit system: ActorSystem): Future[ProductVersion] = {
    import system.dispatcher // execution context for future transformations below
    for {
      Http.HostConnectorInfo(hostConnector, _) <- IO(Http) ? Http.HostConnectorSetup(host, port = 80)
      response <- hostConnector.ask(HttpRequest(GET, "/")).mapTo[HttpResponse]
      _ <- hostConnector ? Http.CloseAll
    } yield {
      system.log.info("Host-Level API: received {} response with {} bytes",
        response.status, response.entity.buffer.length)
      response.header[HttpHeaders.Server].get.products.head
    }
  }

}