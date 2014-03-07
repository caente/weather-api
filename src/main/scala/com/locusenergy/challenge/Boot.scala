package com.locusenergy.challenge

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import com.locusenergy.challenge.services.WeatherServiceActor

object Boot extends App {


  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("weather-challenge")

  // create and start our service actor
  val serviceWords = system.actorOf(Props[WeatherServiceActor], "weather-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(serviceWords, interface = "54.85.65.194", port = 8888)
}