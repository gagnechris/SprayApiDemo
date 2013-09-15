package com.christophergagne.sprayapidemo

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.io.IO
import spray.can.Http

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("spray-api-service")
  val log = Logging(system, getClass)

  // create and start our service actor
  val service = system.actorOf(Props[SprayApiDemoServiceActor], "spray-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}