package com.spark.tutorial

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010

object ApacheKafka extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Spark Streaming")
    .getOrCreate()

  val ssc = new StreamingContext(spark.sparkContext, Seconds(1))

  // TODO:
}
