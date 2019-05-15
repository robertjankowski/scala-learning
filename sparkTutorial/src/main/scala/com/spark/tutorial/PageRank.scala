package com.spark.tutorial

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The PageRank algorithm outputs a probability
  * distribution that represents the likelihood
  * that a person randomly clicking on web links
  * will arrive at a particular web page
  * https://github.com/abbas-taher/pagerank-example-spark2.0-deep-dive
  */
object PageRank extends App {

  val conf = new SparkConf().setAppName("PageRank algorithm").setMaster("local")
  val sc = new SparkContext(conf)

  // load all links
  val textFile = sc.textFile("hollins.dat")
  val pairLinks = textFile
    .map(pair => pair.split(" "))
    .map {
      case Array(key, value) => (key.toInt, value.toInt)
    }
  val links = pairLinks.distinct().groupByKey().cache()
  //  links.collect().take(10).foreach { println }

  // Populating the Ranks Data - Initial sees
  var ranks = links.mapValues(_ => 1.0)

  for (_ <- 1 to 20) {
    val contibs = links.join(ranks).values.flatMap {
      case (urls, rank) =>
        urls.map(url => (url, rank / urls.size))
    }
    ranks = contibs.reduceByKey(_ + _).mapValues(0.15 * 0.85 * _)
  }

  // main page has the highest score
  ranks.sortBy(_._2, ascending = false).take(20).foreach { println }
}
