package com.tutorial.chapter2.enumeration.ifelse

object IfElse extends App {

  val a = 3
  val b = 44

  if (b > a) {
    println(b + a)
  } else {
    println(a * b)
  }

  val n = if (a > b) a else b

}
