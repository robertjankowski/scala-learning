package com.spark.tutorial.avro

import java.io.File

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream, AvroSchema}
import org.apache.avro.Schema

case class User(id: Int, name: String, email: Option[String])

object AvroExample {

  implicit val schema = AvroSchema[User]

  def serialize(users: Seq[User])(implicit schema: Schema) = {
    val os =
      AvroOutputStream.data[User].to(new File("users.avro")).build(schema)
    os.write(users)
    os.flush()
    os.close()
  }

  def deserialize(filename: String)(implicit schema: Schema) = {
    val is =
      AvroInputStream.data[User].from(new File(filename)).build(schema)
    val users = is.iterator.toSeq
    is.close()
    users
  }

  def main(args: Array[String]): Unit = {
    val u1 = User(1, "Rob", None)
    val u2 = User(2, "Bob", Some("bob@gmail.com"))

    // serialize(Seq(u1, u2))
    deserialize("users.avro").foreach { println }
  }

}
