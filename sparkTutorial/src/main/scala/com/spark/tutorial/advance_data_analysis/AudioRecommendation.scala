package com.spark.tutorial.advance_data_analysis

import org.apache.spark.sql.SparkSession

object AudioRecommendation extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Music recommendation using data from Audioscrobbler")
    .config("spark.driver.memory", "6g") // more RAM to process data
    .getOrCreate()

  import spark.implicits._
  val rawUserArtistData = spark.read.textFile("user_artist_data_small.txt")
  val res = rawUserArtistData.map(_.split(' ')(0).toDouble)
  print(res.rdd.stats())

  val rawArtistData = spark.read.textFile("artist_data_small.txt")
  val artistById = rawArtistData
    .flatMap { line =>
      val s = line.split("\\s+", 2)
      val (id, name) = (s(0), s(1))
      if (name.isEmpty) {
        None
      } else {
        try {
          Some((id.toInt, name.trim))
        } catch {
          case _: NumberFormatException => None
        }
      }
    }
    .toDF("id", "artist")
  artistById.show(5)

  val rawArtistAlias = spark.read.textFile("artist_alias_small.txt")
  val artistAlias = rawArtistAlias
    .flatMap { line =>
      val tokens = line.split("\t")
      if (tokens(0).isEmpty) {
        None
      } else {
        Some((tokens(0).toInt, tokens(1).toInt))
      }
    }

  // TODO: first model page 54

}
