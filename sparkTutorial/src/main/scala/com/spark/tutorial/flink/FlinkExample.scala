package com.spark.tutorial.flink

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object FlinkExample {
  def main(args: Array[String]): Unit = {

    var hostname = "localhost"
    var port = 9999

    try {
      val params = ParameterTool.fromArgs(args)
      hostname =
        if (params.has("hostname")) params.get("hostname") else "hostname"
      port = params.getInt("port")
    } catch {
      case _: Exception => {
        System.err.println(
          "No port specified. Please run 'SocketWindowWordCount " +
            "--hostname <hostname> --port <port>', where hostname (localhost by default) and port " +
            "is the address of the text server")
        System.err.println(
          "To start a simple text server, run 'netcat -l <port>' " +
            "and type the input text into the command line")
      }
    }

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text: DataStream[String] = env.socketTextStream(hostname, port, '\n')

    val windowCounts = text
      .flatMap(w => w.split("\\s"))
      .map(w => WordWithCount(w, 1))
      .keyBy("word")
      .timeWindow(Time.seconds(5))
      .sum("count")

    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }
  case class WordWithCount(word: String, count: Long)
}
