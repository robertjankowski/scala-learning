package com.spark.tutorial

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Spark Streaming")
    .getOrCreate()

  val ssc = new StreamingContext(spark.sparkContext, Seconds(1))
  val lines = ssc.socketTextStream("localhost", 9999)
  val words = lines.flatMap(_.split(" "))

  // val pairs = words.map(word => (word, 1))
//  val wordCounts = pairs.reduceByKey(_ + _)
//  wordCounts.print()
//  val windowedWordCounts = pairs.reduceByKeyAndWindow((a: Int, b: Int) => a + b,
//                                                      Seconds(30),
//                                                      Seconds(10))
  // windowedWordCounts.print()

  // SQL solution
  words.foreachRDD { rdd =>
    import spark.implicits._
    val wordsDF = rdd.toDF("word")
    wordsDF.createOrReplaceTempView("words")
    val wordCountsDF =
      spark.sql(
        "SELECT word, COUNT(*) AS total FROM words GROUP BY word ORDER BY total DESC")
    wordCountsDF.show()
  }

  // in different terminal run Netcat
  // nc -lk 9999
  // and write text
  ssc.checkpoint("checkpointDir")
  ssc.start()
  ssc.awaitTermination()
}
