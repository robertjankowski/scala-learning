package tourOfScala

import scala.util.matching.Regex

object RegularExpressions extends App {

  val numberPattern: Regex = "[0-9]".r

  numberPattern.findFirstMatchIn("password") match {
    case Some(_) => println("Password ok")
    case None    => println("Weak password")
  }

  val keyValPattern: Regex = "([0-9a-zA-Z-#()]+): ([0-9a-zA-Z-#() ]+)".r
  val input: String =
    """background-color: #A03300;
      |background-image: url(img/header100.png);
      |background-position: top center;
      |background-repeat: repeat-x;
      |background-size: 2160px 108px;
      |margin: 0;
      |height: 108px;
      |width: 100%;""".stripMargin

  for (patternMatch <- keyValPattern.findAllMatchIn(input)) {
    println(s"key: ${patternMatch.group(1)} | value: ${patternMatch.group(2)}")
  }

  val rob = numberPattern.findAllMatchIn("rob1212jan")
  rob.map(m => m.group(1).toInt)
  println(rob.mkString(","))

}
