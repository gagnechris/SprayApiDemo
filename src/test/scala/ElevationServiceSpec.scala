package com.christophergagne.sprayapidemo

import scala.concurrent.future
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

class ElevationServiceSpec extends FreeSpec with SprayApiDemoService with ScalatestRouteTest with Matchers {
  def actorRefFactory = system

  "The Elevation Service" - {
    "when calling GET api/ElevationService/39/80" - {
      "should return '1159.288940429688'" in {
        Get("/api/ElevationService/39/80") ~> sprayApiDemoRoute ~> check {
          status should equal(OK)
          entity.toString should include("1159.288940429688")
        }
      }
    }
  }
}
