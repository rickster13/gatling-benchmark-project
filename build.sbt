name := "gatling-benchmarks"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val gatlingV = "2.2.2"
  Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingV,
    "io.gatling"            % "gatling-test-framework"    % gatlingV,
    "io.gatling"            % "gatling-app"               % gatlingV,
    "com.typesafe"          % "config"                    % "1.3.1",
    "org.codehaus.groovy"   % "groovy"                    % "2.4.7"
  )
}