package scala_book

trait Observable {
  type Handle
  protected var callbacks = Map[Handle, this.type => Unit]()

  def observe(callback: this.type => Unit): Handle = {
    val handle = createHandle(callback)
    callbacks += (handle -> callback)
    handle
  }

  def unobserve(handle: Handle): Unit = {
    callbacks -= handle
  }

  protected def createHandle(callback: this.type => Unit): Handle

  protected def notifyListeners(): Unit = {
    for (callback <- callbacks.values) callback(this)
  }
}

trait DefaultHandles extends Observable {
  override type Handle = (this.type => Unit)

  override protected def createHandle(callback: this.type => Unit): Handle = callback
}

class VariableStore(private var value: Int) extends Observable with DefaultHandles {
  def get: Int = value

  def set(newValue: Int): Unit = {
    value = newValue
    notifyListeners()
  }

  override def toString: String = " Variable store(" + value + ")"
}

object Foo {
  type T = {
    type U
    def bar: U
  }
  val baz: T = new { // stable reference to T
  type U = String

    def bar: U = "Hello"
  }
}


object Types extends App {

  def test(f: Foo.baz.U) = f // argument type is stable
  println(test(Foo.baz.bar))

  val x = new VariableStore(10)
  val handle = x.observe(println)
  x.set(4)
  x.unobserve(handle)
  x.set(33)

  val xx = new VariableStore(2)
  val yy = new VariableStore(3)
  val callback = println(_: Any)
  val handle1 = xx.observe(callback)
  val handle2 = yy.observe(callback)
  //  yy.unobserve(handle1) <- error type mismatch
  println(handle1 == handle2)

}
