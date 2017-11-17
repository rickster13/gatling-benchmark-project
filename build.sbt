name := "gatling-benchmarks"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  val gatlingV = "2.3.0"
  Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingV,
    "io.gatling"            % "gatling-test-framework"    % gatlingV,
    "io.gatling"            % "gatling-app"               % gatlingV,
    "com.typesafe"          % "config"                    % "1.3.2",
    "org.codehaus.groovy"   % "groovy"                    % "2.4.12"
  )
}