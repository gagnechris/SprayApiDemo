package com.example

import spray.json.{ JsonFormat, DefaultJsonProtocol }

//https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&sensor=true_or_false

//{
//dstOffset: 0,
//rawOffset: -28800,
//status: "OK",
//timeZoneId: "America/Los_Angeles",
//timeZoneName: "Pacific Standard Time"
//}

case class Timezone(timeZoneId: String, timeZoneName: String)
case class GoogleTimezoneApiResult[T](status: String, timeZoneName: String)

object TimezoneJsonProtocol extends DefaultJsonProtocol {
  implicit val timezoneFormat = jsonFormat2(Timezone) 
  implicit def googleTimezoneApiResultFormat[T :JsonFormat] = jsonFormat2(GoogleTimezoneApiResult.apply[T])
}
