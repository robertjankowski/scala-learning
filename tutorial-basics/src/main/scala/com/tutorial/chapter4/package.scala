package com.tutorial

package object chapter4 {

  case class Donut(name: String,
                   price: Double,
                   productCode: Option[Long] = None)

  implicit class AugmentedDonut(donut: Donut) {
    def uuid: String = s"${donut.name} - ${donut.price}"
  }

  type DateTime = org.joda.time.DateTime

}
