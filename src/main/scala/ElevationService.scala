package com.christophergagne.sprayapidemo

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import akka.io.IO

import spray.routing.RequestContext
import spray.httpx.SprayJsonSupport
import spray.client.pipelining._

import scala.util.{ Success, Failure }

object ElevationService {
  case class Process(long: Double, lat: Double)
}

class ElevationService(requestContext: RequestContext) extends Actor {

  import ElevationService._

  implicit val system = context.system
  import system.dispatcher
  val log = Logging(system, getClass)

  def receive = {
    case Process(long,lat) =>
      process(long,lat)
      context.stop(self)
  }

  def process(long: Double, lat: Double) = { 

    log.info("Requesting elevation long: {}, lat: {}", long, lat)

    import ElevationJsonProtocol._
    import SprayJsonSupport._
    val pipeline = sendReceive ~> unmarshal[GoogleElevationApiResult[Elevation]]

    val responseFuture = pipeline{
      Get(s"http://maps.googleapis.com/maps/api/elevation/json?locations=$long,$lat&sensor=false")
    }
    responseFuture onComplete {
      case Success(GoogleElevationApiResult(_, Elevation(_, elevation) :: _)) =>
        log.info("The elevation is: {} m", elevation)
        requestContext.complete(elevation.toString)

      case Failure(error) =>
        requestContext.complete(error)
    }
  }
}
