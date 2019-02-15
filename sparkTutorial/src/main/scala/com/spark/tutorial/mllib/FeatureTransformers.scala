package com.spark.tutorial.mllib

import org.apache.spark.ml.attribute.Attribute
import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object FeatureTransformers extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Feature Transformers")
    .getOrCreate()

  // ----------------
  // Tokenizer
  val sentenceDataFrame = spark
    .createDataFrame(
      Seq(
        (0, "Hi I heard about Spark"),
        (1, "I wish Java could use case classes"),
        (2, "Logistic,regression,models,are,neat")
      ))
    .toDF("id", "sentence")

  val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
  val regexTokenizer = new RegexTokenizer()
    .setInputCol("sentence")
    .setOutputCol("words")
    .setPattern("\\W") // match word

  val countToken = udf { (words: Seq[String]) =>
    words.length
  } // User Defined Function

  val tokenized = tokenizer.transform(sentenceDataFrame)
  tokenized
    .select("sentence", "words")
    .withColumn("tokens", countToken(col("words")))
    .show(false)

  val regexTokenized = regexTokenizer
    .transform(sentenceDataFrame)
  regexTokenized
    .select("sentence", "words")
    .withColumn("tokens", countToken(col("words")))
    .show(false)

  // ----------------
  // StopWordsRemover
  val stopWordsRemover =
    new StopWordsRemover().setInputCol("raw").setOutputCol("filtered")
  val dataSet = spark
    .createDataFrame(
      Seq(
        (0, Seq("I", "saw", "the", "red", "balloon")),
        (1, Seq("Mary", "had", "a", "little", "lamb"))
      ))
    .toDF("id", "raw")
  stopWordsRemover.transform(dataSet).show(false)

  // ----------------
  // N-gram
  val wordDataFrame = spark
    .createDataFrame(
      Seq(
        (0, Array("Hi", "I", "heard", "about", "Spark")),
        (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
        (2, Array("Logistic", "regression", "models", "are", "neat"))
      ))
    .toDF("id", "words")
  val ngram = new NGram().setN(2).setInputCol("words").setOutputCol("ngrams")
  val ngramDF = ngram.transform(wordDataFrame)
  ngramDF.select("ngrams").show(false)

  // ----------------
  // Binarizer
  val dataFrame = spark
    .createDataFrame(Array((0, 0.1), (1, 0.8), (2, 0.2)))
    .toDF("id", "feature")
  val binarizer = new Binarizer()
    .setInputCol("feature")
    .setOutputCol("binarized_feature")
    .setThreshold(0.5)
  binarizer.transform(dataFrame).show(false)

  // ----------------
  // PCA  this example 5D -> 3D
  val data = Array(
    Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
    Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
    Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
  )
  val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")
  val pca = new PCA()
    .setInputCol("features")
    .setOutputCol("pcaFeatures")
    .setK(3)
    .fit(df)
  val result = pca.transform(df).select("pcaFeatures")
  result.show(false)

  // ----------------
  // PolynomialExpansion
  val data1 = Array(
    Vectors.dense(2.0, 1.0),
    Vectors.dense(0.0, 0.0),
    Vectors.dense(3.0, -1.0)
  )
  val df1 = spark.createDataFrame(data1.map(Tuple1.apply)).toDF("features")
  val polyExpansion = new PolynomialExpansion()
    .setInputCol("features")
    .setOutputCol("polyFeatures")
    .setDegree(3)
  polyExpansion.transform(df1).show(false)

  // ----------------
  // Discrete Cosine Transform (DCT)
  val df2 = spark
    .createDataFrame(
      Seq(Vectors.dense(0.0, 1.0, -2.0, 3.0),
          Vectors.dense(-1.0, 2.0, 4.0, -7.0),
          Vectors.dense(14.0, -2.0, -5.0, 1.0)).map(Tuple1.apply))
    .toDF("features")
  val dct = new DCT()
    .setInputCol("features")
    .setOutputCol("featuresDCT")
    .setInverse(false)
  val dctDF = dct.transform(df2)
  dctDF.show(false)

  // ----------------
  // StringIndexer
  val df3 = spark
    .createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    )
    .toDF("id", "category")
  val indexer =
    new StringIndexer().setInputCol("category").setOutputCol("categoryIndex")

  val indexed = indexer.fit(df3).transform(df3)
  indexed.show(false)

  // ----------------
  // IndexToString
  val inputColSchema = indexed.schema(indexer.getOutputCol)
  println(s"${Attribute.fromStructField(inputColSchema).toString()}")

  val converter = new IndexToString()
    .setInputCol("categoryIndex")
    .setOutputCol("originalIndex")
  val converted = converter.transform(indexed)
  converted.show(false)

  // ----------------
  // OneHotEncoderEstimator
  val df4 = spark
    .createDataFrame(
      Seq(
        (0.0, 1.0),
        (1.0, 0.0),
        (2.0, 1.0),
        (0.0, 2.0),
        (0.0, 1.0),
        (2.0, 0.0)
      ))
    .toDF("categoryIndex1", "categoryIndex2")

  val encoder = new OneHotEncoderEstimator()
    .setInputCols(Array("categoryIndex1", "categoryIndex2"))
    .setOutputCols(Array("categoryVec1", "categoryVec2"))
  val model = encoder.fit(df4)
  val encoded = model.transform(df4)
  encoded.show()

  // ----------------
  // VectorIndexer

  // ----------------
  // Interaction
  val df5 = spark
    .createDataFrame(
      Seq(
        (1, 1, 2, 3, 8, 4, 5),
        (2, 4, 3, 8, 7, 9, 8),
        (3, 6, 1, 9, 2, 3, 6),
        (4, 10, 8, 6, 9, 4, 5),
        (5, 9, 2, 7, 10, 7, 3),
        (6, 1, 1, 4, 2, 8, 4)
      ))
    .toDF("id1", "id2", "id3", "id4", "id5", "id6", "id7")
  val assembler1 = new VectorAssembler()
    .setInputCols(Array("id2", "id3", "id4"))
    .setOutputCol("vec1")
    .transform(df5)
  val assembler2 = new VectorAssembler()
    .setInputCols(Array("id5", "id6", "id7"))
    .setOutputCol("vec2")
    .transform(assembler1)
    .select("id1", "vec1", "vec2")

  val iteration = new Interaction()
    .setInputCols(Array("id1", "vec1", "vec2"))
    .setOutputCol("interactedCol")
  val interacted = iteration.transform(assembler2)
  interacted.show(false)

  // ----------------
  // Normalizer
  val dataFrame1 = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 0.5, -1.0)),
        (1, Vectors.dense(2.0, 1.0, 1.0)),
        (2, Vectors.dense(4.0, 10.0, 2.0))
      ))
    .toDF("id", "features")
  val normalizer = new Normalizer()
    .setInputCol("features")
    .setOutputCol("normFeatures")
    .setP(1.0)
  val l1NormData = normalizer.transform(dataFrame1)
  l1NormData.show(false)

  val lInfNormData =
    normalizer.transform(dataFrame1, normalizer.p -> Double.PositiveInfinity)
  lInfNormData.show(false)

  // ----------------
  // StandardScaler
  val scaler = new StandardScaler()
    .setInputCol("")
    .setOutputCol("")
    .setWithMean(true)
    .setWithStd(false)

  // ----------------
  // MinMaxScaler
  val dataFrame2 = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 0.1, -1.0)),
        (1, Vectors.dense(2.0, 1.1, 1.0)),
        (2, Vectors.dense(3.0, 10.1, 3.0))
      ))
    .toDF("id", "features")
  val minMaxScaler = new MinMaxScaler()
    .setInputCol("features")
    .setOutputCol("scaledFeatures")
  val scalerModel = minMaxScaler.fit(dataFrame2)
  val scaledData = scalerModel.transform(dataFrame2)
  scaledData.show(false)

  // ----------------
  // MaxAbsScaler
  val dataFrame3 = spark
    .createDataFrame(
      Seq(
        (0, Vectors.dense(1.0, 0.1, -8.0)),
        (1, Vectors.dense(2.0, 1.0, -4.0)),
        (2, Vectors.dense(4.0, 10.0, 8.0))
      ))
    .toDF("id", "features")
  val maxAbsScaler =
    new MaxAbsScaler().setInputCol("features").setOutputCol("scaledFeatures")
  val maxAbsScalerModel = maxAbsScaler.fit(dataFrame3)
  val scaledData1 = maxAbsScalerModel.transform(dataFrame3)
  scaledData1.select("features", "scaledFeatures").show(false)

  // ----------------
  // Bucketizer
  val splits =
    Array(Double.NegativeInfinity, -0.5, 0.0, 0.5, Double.PositiveInfinity)
  val data4 = Array(-999, -0.5, -0.3, 0.0, 0.21, 9999)
  val dataFrame4 =
    spark.createDataFrame(data4.map(Tuple1.apply)).toDF("features")
  val bucketizer = new Bucketizer()
    .setInputCol("features")
    .setOutputCol("bucketedFeatures")
    .setSplits(splits)
  val bucketedData = bucketizer.transform(dataFrame4)
  bucketedData.show(false)

  // ----------------
  // ElementwiseProduct
  val dataFrame5 = spark
    .createDataFrame(
      Seq(
        ("a", Vectors.dense(1.0, 2.0, 3.0)),
        ("b", Vectors.dense(4.0, 5.0, 6.0))
      ))
    .toDF("id", "vector")
  val transformingVector = Vectors.dense(0.0, 1.0, 2.0)
  val transformer = new ElementwiseProduct()
    .setScalingVec(transformingVector)
    .setInputCol("vector")
    .setOutputCol("transformedVector")
  transformer.transform(dataFrame5).show(false)

  // ----------------
  // SQLTransformer
  val df6 = spark
    .createDataFrame(
      Seq(
        (0, 1.0, 3.0),
        (2, 2.0, 5.3)
      )
    )
    .toDF("id", "v1", "v2")
  val sqlTransformer = new SQLTransformer()
    .setStatement(
      "SELECT *, (v1 + v2) AS v3, (v1 * v2) AS v4 FROM __THIS__"
    )
  sqlTransformer.transform(df6).show(false)

  // ----------------
  // VectorAssembler
  val dataset5 = spark
    .createDataFrame(
      Seq((0, 18, 1.0, Vectors.dense(0.0, 10.0, 0.5), 1.0))
    )
    .toDF("id", "hour", "mobile", "userFeatures", "clicked")
  val assembler = new VectorAssembler()
    .setInputCols(Array("hour", "mobile", "userFeatures"))
    .setOutputCol("features")
  val output = assembler.transform(dataset5)
  output.select("features", "clicked").show(false)

  // ----------------
  // VectorAssembler
  val sizeHint = new VectorSizeHint()
    .setInputCol("userFeatures")
    .setHandleInvalid("skip")
    .setSize(3)
  val datasetWithSize = sizeHint.transform(dataset5)
  datasetWithSize.show(false)

  // ----------------
  // QuantileDiscretizer
  val data2 = Array((0, 18.0), (1, 19.0), (2, 8.0), (3, 5.0), (4, 2.2))
  val df7 = spark.createDataFrame(data2).toDF("id", "hour")
  val discretizer = new QuantileDiscretizer()
    .setInputCol("hour")
    .setOutputCol("result")
    .setNumBuckets(3)
  val result2 = discretizer.fit(df7).transform(df7)
  result2.show(false)

  // ----------------
  // Imputer - missing value mean or median
  val df8 = spark
    .createDataFrame(
      Seq(
        (1.0, Double.NaN),
        (2.0, Double.NaN),
        (Double.NaN, 3.0),
        (4.0, 4.0),
        (5.0, 5.0)
      ))
    .toDF("a", "b")
  val imputer = new Imputer()
    .setInputCols(Array("a", "b"))
    .setOutputCols(Array("out_a", "out_b"))
  val model1 = imputer.fit(df8)
  model1.transform(df8).show(false)

}
