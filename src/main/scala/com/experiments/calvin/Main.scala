package com.experiments.calvin

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

import scala.collection.JavaConverters._

object Main extends App {
  val simClass = classOf[ExampleSimulation].getName
  val props = new GatlingPropertiesBuilder
  val prop: scala.collection.mutable.Map[String, String] = System.getProperties.asScala
  val dataDir = prop.getOrElse("dataDir", IDEPathHelper.dataDirectory.toString)
  props.dataDirectory(dataDir)
  props.resultsDirectory(IDEPathHelper.resultsDirectory.toString)
  props.bodiesDirectory(IDEPathHelper.bodiesDirectory.toString)
  props.binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)
  props.simulationClass(simClass)

  Gatling.fromMap(props.build)
}
