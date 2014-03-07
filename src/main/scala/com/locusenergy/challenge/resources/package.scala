package com.locusenergy.challenge

import spray.json._
import DefaultJsonProtocol._

/**
 * Created: Miguel A. Iglesias
 * Date: 3/5/14
 */
package object resources {


  case class Historic(dates: List[String], values: List[Double])

  implicit val historicFormat = jsonFormat2(Historic)

  case class Error(cause: String)

  implicit val errorFormat = jsonFormat1(Error)



}
