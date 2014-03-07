package com.locusenergy.challenge.resources

import com.locusenergy.challenge.resources.WeatherGuy.Result

/**
 * Created: Miguel A. Iglesias
 * Date: 3/5/14
 */
object Rain extends HistoricWeather {

  def getValue(result: Result): Double = result.precip
}
