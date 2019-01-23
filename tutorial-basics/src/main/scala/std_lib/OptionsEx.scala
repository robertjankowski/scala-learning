package std_lib

object OptionsEx extends App {
  val number: Option[Int] = Some(44)
  val noNumber: Option[Int] = None

  val result1 = number.fold(1)(_ * 10)
  val result2 = noNumber.fold(1)(_ * 10) // return 1 if None
  assert(result1 == 440)
  assert(result2 == 1)
  // return error if assert is false !
}
