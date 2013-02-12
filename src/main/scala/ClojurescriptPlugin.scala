package com.edftwin.play.cljs

import sbt._
import sbt.Keys._
import PlayProject._

object ClojureScriptPlugin extends Plugin with ClojureScriptCompiler {
  val clojureScriptEntryPoints =
    SettingKey[PathFinder]("play-clojurescript-entry-points")
  val clojureScriptOptions =
    SettingKey[Seq[String]]("play-clojurescript-options")

  val clojureScriptCompiler = PlayProject.AssetsCompiler("clojurescript",
    (_ ** "*.cljs"),
    clojureScriptEntryPoints,
    { (name, min) => name.replace(".cljs", if(min) ".min.js" else ".js") },
    { (file, options) => 
      val jsSource = compile(file, options)
      (jsSource, None, Seq(file)) 
    },
    clojureScriptOptions
  )

  val clojureScriptSettings = Seq(
    clojureScriptEntryPoints <<= (sourceDirectory in Compile)(base =>
      ((base / "assets" ** "*.cljs") --- base / "assets" ** "_*")),
    clojureScriptOptions := Seq.empty[String],
    resourceGenerators in Compile <+= clojureScriptCompiler
  )
}
