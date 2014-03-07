package com.locusenergy.challenge.resources

/**
 * Created: Miguel A. Iglesias
 * Date: 3/5/14
 */

import spray.json._
import DefaultJsonProtocol._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class SunStatus(percent_sunny: Double) extends ResponseClass

object Sun extends WeatherGuy[SunStatus] {


  implicit val sunStatusFormat = jsonFormat1(SunStatus)

  def getData(start_date: String, end_date: String, zip: String): Future[SunStatus] =
    retrieveWeather(start_date, end_date, zip).map {
      results =>
        val sunny_days = results.foldLeft(0D) {
          (sun, result) =>
            if (result.cldCvrAvg < 50)
              sun + 1D
            else
              sun
        }
        if (results.isEmpty)
          SunStatus(0)
        else
          SunStatus((sunny_days / results.length * 100).round)
    }
}
