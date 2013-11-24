package com.christophergagne.sprayapidemo

import akka.actor.{ActorSystem, Props}
import akka.actor.ActorDSL._
import akka.event.Logging
import akka.io.IO
import akka.io.Tcp._
import spray.can.Http

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("spray-api-service")
  val log = Logging(system, getClass)

  val callbackActor = actor(new Act {
    become {
      case b @ Bound(connection) => log.info(b.toString)
      case cf @ CommandFailed(command) => log.error(cf.toString)
      case all => log.debug("Management Received a message from Akka.IO: " + all.toString)
    }
  })

  // create and start our service actor
  val service = system.actorOf(Props[SprayApiDemoServiceActor], "spray-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http).tell(Http.Bind(service, "localhost", 8080), callbackActor)
}