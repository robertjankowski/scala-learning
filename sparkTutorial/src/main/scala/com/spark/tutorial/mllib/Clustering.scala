package com.spark.tutorial.mllib

import org.apache.spark.ml.clustering.{
  BisectingKMeans,
  GaussianMixture,
  KMeans,
  LDA
}
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.sql.SparkSession

object Clustering extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Clustering")
    .getOrCreate()

  // ----------
  // KMeans
  val dataset = spark.read.format("libsvm").load("sample_kmeans_data.txt")
  val kmeans = new KMeans().setK(2).setSeed(10L)
  val model = kmeans.fit(dataset)
  val predictions = model.transform(dataset)

  val evaluator = new ClusteringEvaluator()
  val silhouette = evaluator.evaluate(predictions)
  println(s"Silhouette with squared euclidean distance = $silhouette")
  println("Clusters centers:")
  model.clusterCenters.foreach { println }

  // ----------
  // LDA
  val dataset1 = spark.read.format("libsvm").load("sample_lda_libsvm_data.txt")
  val lda = new LDA().setK(10).setMaxIter(10)
  val model1 = lda.fit(dataset1)

  val ll = model1.logLikelihood(dataset1)
  val lp = model1.logPerplexity(dataset1)
  println(s"The lower bound on the log likelihood of the entire corpus: ${ll}")
  println(s"The upper bound on perplexity: ${lp}")

  val topics = model1.describeTopics(3)
  println("The topics described by their top-weighted terms:")
  topics.show(false)
  model1.transform(dataset1).show(false)

  // ----------
  // Bisecting KMeans
  val bkm = new BisectingKMeans().setK(2).setSeed(1L)
  val model2 = bkm.fit(dataset)

  val cost = model2.computeCost(dataset)
  println(s"Within set sum of squared errors = ${cost}")

  println("Clusters centers:")
  model2.clusterCenters.foreach { println }

  // ----------
  // Gaussian Mixture Model (GMM)
  val gmm = new GaussianMixture().setK(2)
  val model3 = gmm.fit(dataset)

  for (i <- 0 until model3.getK) {
    println(s"Gaussian $i:\nweight=${model3
      .weights(i)}\nmu=${model3.gaussians(i).mean}\nsigma=\n${model3.gaussians(i).cov}\n")
  }
}
