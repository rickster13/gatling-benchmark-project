package com.experiments.calvin

import com.experiments.calvin.ExampleSimulation._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

object ExampleSimulation {

  def postCollectors(duration: Duration) = during(duration) {
    // ${collectorNumber} is obtained through Gatling feeder
    exec(
      http("create members")
        .post("/members")
        .body(StringBody(
          """
            |{
            | "collectorNumber": "${collectorNumber}"
            |}
          """.stripMargin))
        .asJSON
    )
  }
}

class ExampleSimulation extends Simulation {
  val props: mutable.Map[String, String] = System.getProperties.asScala
  val testDuration = props.getOrElse("duration", "600").toInt
  val users = props.getOrElse("users", "10").toInt
  val baseUrl = props.getOrElse("baseUrl", "http://localhost")
  val randomCollectorNumberFeeder = Iterator continually {
    Map {
      "collectorNumber" -> (Random.nextInt(Integer.MAX_VALUE) + 10000000000L)
    }
  }

  val protocol = http.baseURL(baseUrl).acceptHeader("*/*")
  val simulationScenario =
    scenario("Create Members Simulation")
      .feed(randomCollectorNumberFeeder)
      .exec(postCollectors(testDuration))

  // Run tests using the closed model (connection re-use)
  setUp {
    simulationScenario
      .inject(rampUsers(users) over (0.10 * testDuration seconds))
      .protocols(protocol)
  }
}
