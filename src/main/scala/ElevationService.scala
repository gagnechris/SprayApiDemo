package com.example 

import akka.actor.{ Actor, ActorRef, Props }

import spray.routing.RequestContext
import spray.httpx.SprayJsonSupport
import spray.http._
import spray.util._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import scala.util.{ Success, Failure }

object ElevationService {

  case class Process(long: Double, lat: Double)
}

class ElevationService(googleApiConduit: ActorRef, requestContext: RequestContext) extends Actor {

  import ElevationService._

  def receive = {
    case Process(long,lat) =>
      process(long,lat)
      context.stop(self)
  }

  def process(long: Double, lat: Double) = { 

    import spray.client.HttpConduit._
    import ElevationJsonProtocol._
    import SprayJsonSupport._
    
    val pipeline = sendReceive(googleApiConduit) ~> unmarshal[GoogleElevationApiResult[Elevation]]
    val response = pipeline(Get(s"/maps/api/elevation/json?locations=$long,$lat&sensor=false"))
    response onComplete {
      case Success(response) =>
        requestContext.complete(response.results.head.elevation.toString)

      case Failure(error) =>
        requestContext.complete(error)
    }
  }
}
