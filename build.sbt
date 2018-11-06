import sbt._
scalaVersion := "2.12.5"
version      := "0.2.1"
name := "scala-hive-executor"

mainClass in assembly := Some("com.reckoner.cdp.Main")
assemblyJarName in assembly := "scala-hive-executor.jar"

scalacOptions += "-deprecation"
crossPaths := false

cancelable in Global := true

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.6.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-api-scala_2.12" % "11.0",
  "com.google.guava" % "guava" % "15.0",
  ("org.apache.hive" % "hive-jdbc" % "2.1.1").
    exclude("org.apache.zookeeper","zookeeper").
    exclude("org.apache.logging.log4j","log4j-slf4j-impl").
    exclude("org.slf4j","slf4j-log4j12"),
  "org.apache.hadoop" % "hadoop-client" % "2.8.1",
  "com.typesafe" % "config" % "1.3.1",
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.google.cloud.sql" % "postgres-socket-factory" % "1.0.8",
  "com.github.scopt" %% "scopt" % "3.6.0"
)



assemblyShadeRules in assembly := Seq(
 ShadeRule.rename("org.datanucleus.**" -> "shaded.org.datanucleus.@1").inLibrary("org.apache.hive" % "hive-jdbc" % "2.3.2")
)



assemblyMergeStrategy in assembly := {
 case PathList(ps @ _*) if ps.last == "Log4j2Plugins.dat" => MergeStrategy.first
 case PathList("org.apache.logging", xs @ _*) => MergeStrategy.first
 case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
 case PathList("javax", "el", xs @ _*) => MergeStrategy.first
 case PathList("javax", "jdo", xs @ _*) => MergeStrategy.first
 case PathList("javax", "transaction", xs @ _*) => MergeStrategy.first
 case PathList("org", "apache", xs @ _*) => MergeStrategy.first
 case PathList(ps @ _*) if ps.last endsWith ".gif" => MergeStrategy.discard
 case PathList(ps @ _*) if ps.last endsWith ".js" => MergeStrategy.discard
 case PathList(ps @ _*) if ps.last endsWith ".css" => MergeStrategy.discard
 case PathList(ps @ _*) if ps.last endsWith ".ico" => MergeStrategy.discard
 case PathList(ps @ _*) if ps.last endsWith "log4j2.xml" => MergeStrategy.last
 case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.last
 case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
 case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
