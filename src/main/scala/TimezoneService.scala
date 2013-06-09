package com.example 

import akka.actor.{ Actor, ActorRef, Props }

import spray.routing.RequestContext
import spray.httpx.SprayJsonSupport
import spray.http._
import spray.util._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import scala.util.{ Success, Failure }

object TimezoneService {

  case class Process(long: Double, lat: Double, timestamp: String)
}

class TimezoneService(googleApiConduit: ActorRef, requestContext: RequestContext) extends Actor {

  import TimezoneService._

  def receive = {
    case Process(long,lat,timestamp) =>
      process(long,lat,timestamp)
      context.stop(self)
  }

  def process(long: Double, lat: Double, timestamp: String) = { 

    import spray.client.HttpConduit._
    import TimezoneJsonProtocol._
    import SprayJsonSupport._
    
    val pipeline = sendReceive(googleApiConduit) //~> unmarshal[GoogleTimezoneApiResult[Timezone]]
    val response = pipeline(Get(s"/maps/api/timezone/json?location=$long,$lat&timestamp=$timestamp&sensor=false"))
    response onComplete {
      case Success(response) =>
        println(response)
        requestContext.complete(response)//.timeZoneName)

      case Failure(error) =>
        requestContext.complete(error)
    }
  }
}
