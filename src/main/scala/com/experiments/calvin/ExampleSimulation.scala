package com.experiments.calvin

import java.util.UUID

import com.experiments.calvin.ExampleSimulation._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.duration._
import scala.language.postfixOps

object ExampleSimulation {

  def createMember(duration: Duration) = during(duration) {
    // ${collectorNumber} is obtained through Gatling feeder
    exec(
      http("create members")
        .post("/members")
        .body(StringBody(
          """
            |{
            | "member": "${memberId}"
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
  val randomMemberIdFeeder = Iterator continually {
    Map {
      "memberId" -> UUID.randomUUID()
    }
  }

  val protocol = http.baseURL(baseUrl).acceptHeader("*/*")
  val simulationScenario =
    scenario("Create Members Simulation")
      .feed(randomMemberIdFeeder)
      .exec(createMember(testDuration))

  // Run tests using the closed model (connection re-use)
  setUp {
    simulationScenario
      .inject(rampUsers(users) over (0.10 * testDuration seconds))
      .protocols(protocol)
  }
}
