organization := "com.edftwin"

name := "play-cljs"

version := "1.0-SNAPSHOT"

sbtPlugin := true

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "org.clojure" % "clojure" % "1.4.0",
  "org.clojure" % "clojurescript" % "0.0-1576"
)

addSbtPlugin("play" % "sbt-plugin" % "2.1.0")
