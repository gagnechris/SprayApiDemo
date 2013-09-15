package com.christophergagne.sprayapidemo

import scala.concurrent.future
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

class TimezoneServiceSpec extends FreeSpec with SprayApiDemoService with ScalatestRouteTest with Matchers {
  def actorRefFactory = system

  "The Timezone Service" - {
    "when calling GET /api/TimezoneService/39/-119/1331161200" - {
      "should return 'Pacific Standard Time'" in {
        Get("/api/TimezoneService/39/-119/1331161200") ~> sprayApiDemoRoute ~> check {
          status should equal(OK)
          entity.toString should include("Pacific Standard Time")
        }
      }
    }
  }
}
