package logging

import com.typesafe.scalalogging.LazyLogging

object HelloWorldLogging extends App with LazyLogging {

  logger.info("Scala Logging")
  logger.error("Error message")

}