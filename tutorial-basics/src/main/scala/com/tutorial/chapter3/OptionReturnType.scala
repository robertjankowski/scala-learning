package com.tutorial.chapter3

object OptionReturnType extends App {
  def dailyCouponCode(): Option[String] = {
    val couponFromDB = "COUPON 12" // "" for empty string
    Option(couponFromDB).filter(_.nonEmpty)
  }

  val coupon = dailyCouponCode()
  println(coupon.getOrElse("No coupon"))

  dailyCouponCode match {
    case Some(value) => println(s"There is a coupon: ${value}")
    case None        => println("No coupon available")
  }

  val todayCoupon =
    dailyCouponCode().fold("No coupon available")(coupon => coupon)

}
