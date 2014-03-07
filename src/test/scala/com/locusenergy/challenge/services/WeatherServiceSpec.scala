package com.locusenergy.challenge.services

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import com.locusenergy.challenge.resources
import resources._
import spray.httpx.unmarshalling._
import spray.json._
import spray.httpx.SprayJsonSupport._

class WeatherServiceSpec extends Specification with Specs2RouteTest with WeatherService {
  def actorRefFactory = system
  import resources.historicFormat
  import Sun.sunStatusFormat

  "WeatherService" should {

    "returns the default handler message" in {
      Get("/weather/gibberish?start_date=2014-01-01&end_date=2014-03-7&zip=33020") ~> myRoute ~> check {
       status === Conflict
      }
    }

    "returns the rain historic" in {
      Get("/weather/rain?start_date=2014-03-01&end_date=2014-03-7&zip=33020") ~> myRoute ~> check {
        responseAs[Historic] === Historic(
          List("2014-03-01", "2014-03-02", "2014-03-03", "2014-03-04", "2014-03-05", "2014-03-06"),
          List(0.0, 0.0, 0.0, 0.0, 0.65, 0.36))
        status === OK
      }
    }

    "returns the temperature historic" in {
      Get("/weather/temperature?start_date=2014-03-01&end_date=2014-03-7&zip=33020") ~> myRoute ~> check {
        responseAs[Historic] === Historic(
          List("2014-03-01", "2014-03-02", "2014-03-03", "2014-03-04", "2014-03-05", "2014-03-06"),
          List(69.1, 73.0, 73.5, 72.5, 75.7, 73.0))
        status === OK
      }
    }

    "returns the sunny days percent" in {
      Get("/weather/sun?start_date=2014-03-01&end_date=2014-03-7&zip=33020") ~> myRoute ~> check {
        responseAs[SunStatus] === SunStatus(67.0)
        status === OK
      }
    }

  }
}
