package com.locusenergy.challenge.resources

import com.locusenergy.challenge.resources.WeatherGuy.Result
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.text.{SimpleDateFormat, DateFormat}
import org.joda.time.{DateTime, Duration}

/**
 * Created: Miguel A. Iglesias
 * Date: 3/6/14
 */
trait HistoricWeather extends WeatherGuy[Historic] {

  val dateFormat = new SimpleDateFormat("MM/dd/yyyy")

  def getDate(string_date: String): String = string_date.substring(0, string_date.indexOf("T"))

  def getValue(result: Result): Double

  def foldHistoric(results: List[Result]): Historic =
    results.foldRight(Historic(List.empty[String], List.empty[Double])) {
      (result, historic) =>
        Historic(
          getDate(result.timestamp) :: historic.dates,
          getValue(result) :: historic.values
        )
    }

  def fillEmpty(start_date: String, end_date: String): List[Result] = {
    val d1 = new DateTime(dateFormat.parse(start_date))
    val d2 = new DateTime(dateFormat.parse(end_date))
    val interval = new Duration(d2, d1)
    val dates = (0L to interval.getStandardDays).foldRight(List(d2)) {
      (day, days) =>
        days.head.minusDays(1) :: days
    }
    dates.map {
      date =>
        Result(dateFormat.format(date.toDate) + "T00:00:00-05:00", 0, 0, 0, 0, 0)
    }
  }

  def getData(start_date: String, end_date: String, zip: String): Future[Historic] =
    retrieveWeather(start_date, end_date, zip) map {
      results =>
        if (results.isEmpty)
          foldHistoric(fillEmpty(start_date, end_date))
        else
          foldHistoric(results)
    }
}
