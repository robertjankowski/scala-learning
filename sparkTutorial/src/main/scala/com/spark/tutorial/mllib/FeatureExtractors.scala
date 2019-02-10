package com.spark.tutorial.mllib

import org.apache.spark.ml.feature._
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.linalg.Vector

object FeatureExtractors extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Feature Extractors")
    .getOrCreate()

  // TF-IDF
  val sentenceData = spark
    .createDataFrame(
      Seq(
        (0.0, "Hi I heard about Spark"),
        (0.0, "I wish Java could use case classes"),
        (1.0, "Logistic regression models are neat")
      ))
    .toDF("label", "sentence")

  val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
  val wordsData = tokenizer.transform(sentenceData)
//  val a = wordsData.collect()
//  a.foreach(println)
  val hashingTF = new HashingTF()
    .setInputCol("words")
    .setOutputCol("rawFeatures")
    .setNumFeatures(20)

  val featurizedData = hashingTF.transform(wordsData)

  val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  val idfModel = idf.fit(featurizedData)

  val rescaledData = idfModel.transform(featurizedData)
//  rescaledData.select("label", "features").show(false)

  // Word2Vec
  val documentDF = spark
    .createDataFrame(
      Seq(
        "Hi I heard about Spark".split(" "),
        "I wish Java could use case classes".split(" "),
        "Logistic regression models are neat".split(" ")
      ).map(Tuple1.apply))
    .toDF("text")
  val word2Vec = new Word2Vec()
    .setInputCol("text")
    .setOutputCol("result")
    .setVectorSize(13)
    .setMinCount(0)
  val model = word2Vec.fit(documentDF)

  val result = model.transform(documentDF)
  result.collect().foreach {
    case Row(text: Seq[_], features: Vector) =>
      println(s"Text: [${text.mkString(", ")}] =>\nVector: $features\n")
  }

  // CountVectorizer
  val df = spark
    .createDataFrame(
      Seq(
        (0, Array("a", "b", "c")),
        (1, Array("a", "b", "b", "c", "a"))
      ))
    .toDF("id", "words")
  val cvModel: CountVectorizerModel = new CountVectorizer()
    .setInputCol("words")
    .setOutputCol("features")
    .setVocabSize(3)
    .setMinDF(2)
    .fit(df)
  cvModel.transform(df).show(false)

  // Feature Hasher
  val dataset = spark
    .createDataFrame(
      Seq(
        (2.2, true, "1", "foo"),
        (3.3, false, "2", "bar"),
        (4.4, false, "3", "baz"),
        (5.5, false, "4", "foo")
      ))
    .toDF("real", "bool", "stringNum", "string")
  val hasher = new FeatureHasher()
    .setInputCols("real", "bool", "stringNum", "string")
    .setOutputCol("features")
  val featurized = hasher.transform(dataset)
  featurized.show(false)

}
