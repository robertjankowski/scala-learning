package com.spark.tutorial.files

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.spark.sql.SparkSession

object SequenceFilesLoading extends App {

  val spark = SparkSession
    .builder()
    .master("local")
    .appName("Sequence Files Loading")
    .getOrCreate()

  val sc = spark.sparkContext

  val input = sc
    .textFile("seqText.txt")
    .map { l =>
      val line = l.split(" ")
      (line(0).toInt, line(1))
    }

  input.foreach(value => println(s"key: ${value._1}, value: ${value._2}"))

  // save as Sequence File
  input.saveAsSequenceFile("SeqFile")

  // load Sequence File
  val data = sc.sequenceFile("SeqFile", classOf[IntWritable], classOf[Text])
  data.foreach { println }

}
