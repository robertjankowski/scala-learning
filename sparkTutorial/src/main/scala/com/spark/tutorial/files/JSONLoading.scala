package com.spark.tutorial.files

import org.apache.spark.sql.SparkSession

class Person(name: String, age: Int, lovesPandas: Boolean) {
  val pName = name
  val pAge = age
  val pLovesPandas = lovesPandas
}

object Person {
  def apply(name: String, age: Int, lovesPandas: Boolean): Person =
    new Person(name, age, lovesPandas)
}

object JSONLoading extends App {

  val spark = SparkSession
    .builder()
    .master("local")
    .appName("JSON loading app")
    .getOrCreate()

  //  One JSON per line
  //  val people = spark.read.json("example.json")

  // Normal JSON format
  val people = spark.read
    .option("multiline", true)
    .json("example.json")
  people.printSchema()
  people.show()

  // one row
  people.foreach(row => println(s"Row: ${row}"))
  val row = people.rdd.first().getSeq[Person](0)
  row.foreach { println }

  // save to file only people that love Pandas
  val sc = spark.sparkContext
  //  TODO: Exception in thread "main" java.lang.ArrayStoreException:
  //   org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
  //  val toSave = sc.parallelize(row)
  //  toSave.foreach { println }

}
