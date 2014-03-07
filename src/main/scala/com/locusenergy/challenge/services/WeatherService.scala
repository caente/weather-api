package com.locusenergy.challenge.services

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling._
import spray.httpx.unmarshalling._
import spray.json._
import com.locusenergy.challenge.resources.Temperature
import com.locusenergy.challenge.resources.Rain
import com.locusenergy.challenge.resources.Sun
import scala.util.{Failure, Success}
import spray.http.HttpMethods._
import scala.util.Success
import com.locusenergy.challenge.resources.Error
import scala.util.Failure

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class WeatherServiceActor extends Actor with WeatherService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait WeatherService extends HttpService {

  implicit val exec = actorRefFactory.dispatcher
  import Sun.sunStatusFormat

  val corsHeaders = List(HttpHeaders.`Access-Control-Allow-Origin`(AllOrigins),
    HttpHeaders.`Access-Control-Allow-Methods`(GET, POST, OPTIONS, DELETE),
    HttpHeaders.`Access-Control-Allow-Headers`("Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, User-Agent"))

  val myRoute = respondWithHeaders(corsHeaders: _*) {
    parameters('start_date, 'end_date, 'zip) {
      (start_date, end_date, zip) =>
        respondWithMediaType(`application/json`) {
          path("sun") {
            onComplete(Sun.getData(start_date, end_date, zip)) {
              case Success(value) =>
                complete(value)
              case Failure(e) => complete{
                StatusCodes.FailedDependency -> Error(e.getMessage)
              }
            }
          } ~
            path("temperature") {
              onComplete(Temperature.getData(start_date, end_date, zip)) {
                case Success(value) => complete(value)
                case Failure(e) => complete{
                  StatusCodes.FailedDependency -> Error(e.getMessage)
                }
              }
            } ~
            path("rain") {
              onComplete(Rain.getData(start_date, end_date, zip)) {
                case Success(value) => complete(value)
                case Failure(e) => complete{
                  StatusCodes.FailedDependency -> Error(e.getMessage)
                }
              }

            }
        }
    }
  }
}
