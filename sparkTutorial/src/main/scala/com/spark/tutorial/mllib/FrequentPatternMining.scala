package com.spark.tutorial.mllib

import org.apache.spark.ml.fpm.{FPGrowth, PrefixSpan}
import org.apache.spark.sql.SparkSession

object FrequentPatternMining extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Frequent Pattern Mining")
    .getOrCreate()

  // ---------
  // FP-Growth
  import spark.implicits._
  val dataset = spark
    .createDataset(
      Seq(
        "1 2 5",
        "1 2 3 5",
        "1 2"
      ))
    .map(t => t.split(" "))
    .toDF("items")

  val fpGrowth =
    new FPGrowth().setItemsCol("items").setMinSupport(0.5).setMinConfidence(0.6)
  val model = fpGrowth.fit(dataset)

  // display frequent itemsets
  model.freqItemsets.show()

  // display generated association rules
  model.associationRules.show()

  model.transform(dataset).show()

  // ------------
  // PrefixSpan
  val smallTestData = Seq(Seq(Seq(1, 2), Seq(3)),
                          Seq(Seq(1), Seq(3, 2), Seq(1, 2)),
                          Seq(Seq(1, 2), Seq(5)),
                          Seq(Seq(6)))
  val df = smallTestData.toDF("sequence")
  val result = new PrefixSpan()
    .setMinSupport(0.5)
    .setMaxPatternLength(5)
    .setMaxLocalProjDBSize(3200000)
    .findFrequentSequentialPatterns(df)
    .show()
}
