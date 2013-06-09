package com.example

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }

import spray.can.server.HttpServer
import spray.can.client.DefaultHttpClient
import spray.io.{ IOExtension, SingletonHandler }
import spray.routing.HttpServiceActor
import spray.client.HttpConduit

object SprayApiServiceApp extends App {
  
  val system = ActorSystem("spray-api-service")
  val httpClient = DefaultHttpClient(system)
  val ioBridge = IOExtension(system).ioBridge()
  val googleApiConduit = system.actorOf(Props(new HttpConduit(httpClient, "maps.googleapis.com", port = 443, sslEnabled = true)), "google-maps-api-http-conduit")
  val handler = system.actorOf(Props(new SprayApiService(googleApiConduit)), "spray-service-handler")
  val server = system.actorOf(Props(new HttpServer(ioBridge, SingletonHandler(handler))), "http-server")
  server ! HttpServer.Bind("localhost", 8080)

  println("Press ENTER to exit")
  Console.readLine()
  system.shutdown()
}

class SprayApiService(googleApiConduit: ActorRef) extends Actor with HttpServiceActor {

  def receive = runRoute(
    path("api/ElevationService" / DoubleNumber / DoubleNumber){ (long, lat) => 
      get { 
          requestContext =>
            val elevationService = context.actorOf(Props(new ElevationService(googleApiConduit, requestContext)))
            elevationService ! ElevationService.Process(long, lat)
      }
    } ~
    path("api/TimezoneService" / DoubleNumber / DoubleNumber / PathElement){ (long, lat, timestamp) =>
      get {
        requestContext =>
          val timezoneService = context.actorOf(Props(new TimezoneService(googleApiConduit, requestContext)))
          timezoneService ! TimezoneService.Process(long, lat, timestamp)
      }
    }
  )
}
