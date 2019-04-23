package scala_book

object PolymorphismEquality extends App {

  val x = new InstantaneousTime {
    override val repr: Int = 2
  }
  val y = new Event {
    override val name: String = "Test"
    override val repr: Int = 2
  }

  trait InstantaneousTime extends Equals {
    val repr: Int

    override def equals(other: Any): Boolean = other match {
      case that: InstantaneousTime =>
        if (this eq that) {
          true
        } else {
          (that.## == this.##) &&
          (repr == that.repr) &&
          (that canEqual this)
        }
      case _ => false
    }

    override def canEqual(other: Any): Boolean =
      other.isInstanceOf[InstantaneousTime]

    override def hashCode(): Int = repr.hashCode
  }

  trait Event extends InstantaneousTime {
    val name: String

    override def equals(other: Any): Boolean = other match {
      case that: Event =>
        if (this eq that) {
          true
        } else {
          (that.## == this.##) &&
          (repr == that.repr) &&
          (that canEqual this)
        }
      case _ => false
    }

    override def canEqual(other: Any): Boolean = other.isInstanceOf[Event]
  }

  println(y == x)
  println(x == y)
}
