package postgres

import slick.lifted.{TableQuery, Tag}
import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object CaseClassMappping extends App {

  val users = TableQuery[Users]

  val db = Database.forConfig("h2mem1")
  try {
    Await.result(db.run(DBIO.seq(
      // create the schema
      users.schema.create,

      // insert two User instances
      users += User("John Doe"),
      users += User("Fred Smith"),

      // print the users (select * from USERS)
      users.result.map(println)
    )), Duration.Inf)
  } finally db.close
}


case class User(name: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "USERS") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME")

  override def * = (name, id.?) <> (User.tupled, User.unapply)
}