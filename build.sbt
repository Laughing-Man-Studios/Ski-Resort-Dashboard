import scala.sys.process._

name := """ski-resort-dashboard"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
lazy val buildFrontEnd = taskKey[Unit]("Run npm install and execute typescript compilation");

buildFrontEnd := {
      val s: TaskStreams = streams.value
      val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
      val npmInstall: Seq[String] = shell :+ "npm install"
      val npmBuild: Seq[String] = shell :+   "npm run build"
      val frontEndPath = baseDirectory.value /"front-end"
      s.log.info("building frontend...")
      if((Process(npmInstall, frontEndPath) #&& Process(npmBuild, frontEndPath) !) == 0) {
        s.log.success("frontend build successful!")
      } else {
        throw new IllegalStateException("frontend build failed!")
      }
}
(Compile / compile) := ((Compile / compile) dependsOn buildFrontEnd).value

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += ws
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.2.1"
libraryDependencies += jdbc
libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
play.sbt.routes.RoutesKeys.routesImport += "util.Binders._"

// Remove Documenation From Production Build
Compile / doc / sources := Seq.empty
Compile / packageDoc / publishArtifact := false
