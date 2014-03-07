package com.locusenergy.challenge.resources

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.locusenergy.challenge.resources.WeatherGuy.Result
import scala.io.Source

/**
 * Created: Miguel A. Iglesias
 * Date: 3/6/14
 */
class HistoricWeatherSuite extends FunSuite with ShouldMatchers {

  val historic = new HistoricWeather {
    override def getValue(result: Result): Double = result.tempMin
  }


  test("should fill an empty response") {
    historic.fillEmpty("03/06/2014", "03/06/2014") should be === List(Result("03/05/2014T00:00:00-05:00",0,0.0,0.0,0.0,0.0), Result("03/06/2014T00:00:00-05:00",0,0.0,0.0,0.0,0.0))

  }

}
