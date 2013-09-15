package com.christophergagne.sprayapidemo

import akka.actor.{Actor, Props}
import akka.event.Logging
import spray.routing._
import spray.http._
import MediaTypes._

class SprayApiDemoServiceActor extends Actor with SprayApiDemoService {
  
  def actorRefFactory = context

  def receive = runRoute(sprayApiDemoRoute)
}

trait SprayApiDemoService extends HttpService {
  val sprayApiDemoRoute =
    pathPrefix("api") {
      path("ElevationService" / DoubleNumber / DoubleNumber) { (long, lat) =>
        requestContext =>
          val elevationService = actorRefFactory.actorOf(Props(new ElevationService(requestContext)))
          elevationService ! ElevationService.Process(long, lat)
      } ~
      path("TimezoneService" / DoubleNumber / DoubleNumber / Segment) { (long, lat, timestamp) =>
        requestContext =>  
          val timezoneService = actorRefFactory.actorOf(Props(new TimezoneService(requestContext)))
          timezoneService ! TimezoneService.Process(long, lat, timestamp)
      }
    }
}
