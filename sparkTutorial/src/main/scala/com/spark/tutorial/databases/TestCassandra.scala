package com.spark.tutorial.databases

import org.apache.spark.{SparkConf, SparkContext}

/**
  * How to open terminal with Cassandra CQL
  * 1. ./cassandra -f (to activate host)
  * 2. activate python2 (anaconda-navigator -> select python2 and open terminal)
  * 3. ./bin/cqlsh localhost (to write CQL queries)
  */
object TestCassandra extends App {

  /* Example in `cqlsh`:

  > CREATE SCHEMA schema1
       WITH replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
  > USE schema1;
  > CREATE TABLE users (
                 user_id varchar PRIMARY KEY,
                 first varchar,
                 last varchar,
                 age int
               );
  > INSERT INTO users (user_id, first, last, age) VALUES ('jsmith', 'John', 'Smith', 22);
  > SELECT * FROM users;

   */
  val conf =
    new SparkConf(true)
      .setMaster("local")
      .setAppName("Cassandra connection test")
      .set("spark.cassandra.connection.host", "localhost")
  val sc = new SparkContext(conf)

  // Unfortunately Cassandra + Spark library (datastax) works on Scala 2.11 not 2.12 ;(
  // That's why I got the error!

  //  val data = sc.cassandraTable("playgroud", "users")
  //  print(data.count())

}
