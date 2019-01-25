package com.tutorial.chapter2.enumeration.string_manipulation

object StringEscape extends App {

  var donutJson: String =
    "{\"donut_name\":\"Glazed Donut\",\"taste_level\":\"Very Tasty\",\"price\":2.50}"
  println(donutJson)
  val donutJson1: String =
    """
      #{
      #"donut_name": "Glazed Donut",
      #"price": 2.4
      #}
    """.stripMargin('#')
  println(donutJson1)
}
