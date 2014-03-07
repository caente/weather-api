package com.locusenergy.challenge

import spray.json._
import DefaultJsonProtocol._

/**
 * Created: Miguel A. Iglesias
 * Date: 3/5/14
 */
package object resources {


  trait ResponseClass

  case class Historic(dates: List[String], values: List[Double]) extends ResponseClass

  implicit val historicFormat = jsonFormat2(Historic)

  case class Error(cause: String)

  implicit val errorFormat = jsonFormat1(Error)

  import Sun.sunStatusFormat

  object ResponseProtocol extends DefaultJsonProtocol {
    implicit object ResponseClassFormat extends RootJsonFormat[ResponseClass] {
      override def read(json: JsValue): ResponseClass = ???

      override def write(obj: ResponseClass): JsValue = obj match {
        case h:Historic => h.toJson
        case s:SunStatus => s.toJson
      }
    }

  }


}
