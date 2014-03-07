package com.locusenergy.challenge.resources

import scala.concurrent.Future
import dispatch._
import org.json4s.DefaultFormats
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created: Miguel A. Iglesias
 * Date: 3/6/14
 */
object WeatherGuy {
  implicit val formats = DefaultFormats


  case class Result(timestamp: String, cldCvrAvg: Int, precip: Double, tempMin: Double, tempAvg: Double, tempMax: Double)

}

trait WeatherGuy[A] {

  import WeatherGuy._

  def getData(start_date: String, end_date: String, zip: String):Future[A]

  def retrieveWeather(start_date: String, end_date: String, zip: String): Future[List[Result]] = {
    val parameters = Map(
      "limit" -> "25",
      "postal_code_eq" -> zip,
      "country_eq" -> "US",
      "fields" -> "postal_code,country,timestamp,tempMax,tempAvg,tempMin,precip,cldCvrAvg",
      "timestamp_between" -> s"$start_date,$end_date"
    )
    val svc = url("https://api.weathersource.com/v1/f7c4184d38fa32bec4cb/history_by_postal_code.json") <<? parameters
    Http(svc OK as.json4s.Json).map {
      jsValue =>
        jsValue.extract[List[Result]]
    }
  }
}
