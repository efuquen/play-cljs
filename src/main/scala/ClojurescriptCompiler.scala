package com.edftwin.play.cljs

import sbt._

import clojure.lang.RT
import clojure.lang.Var

trait ClojureScriptCompiler { 
  plugin: Plugin => 


  private lazy val buildFunc = getBuildFunc

  private def getBuildFunc: Var = {
    Thread.currentThread().setContextClassLoader(this.getClass.getClassLoader)
    RT.loadResourceScript("cljsc.clj")
    RT.`var`("com.edftwin.cljs", "build")
  }

  def compile(cljs: File, options: Seq[String]): String = {
    //TODO: unused for now
    val optionMap = seqToMap(options)
    //TODO: remove hardcoding of public javascript folder
    buildFunc.invoke(cljs.getPath,
      "{:output-dir \"public/javascripts/\" :output-to nil}").asInstanceOf[String]
  }

  private def seqToMap(seq: Seq[String]): Map[String, String] = {
    if(seq.size % 2 != 0) {
      throw new ClojureScriptCompilerException(
        "Incorrect number of values in options sequence. Size: " + seq.size)
    } else {
      var i = 0
      val map = scala.collection.mutable.Map[String,String]()
      while(i < seq.size) {
        map += seq(i) -> seq(i+1)
        i += 2
      }
      Map[String,String]() ++ map
    }
  }
}

class ClojureScriptCompilerException(msg: String) extends Exception(msg)
