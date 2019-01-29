package com.tutorial.chapter5

trait Talking {

  def say: Unit
}

trait Eating {

  def eat(what: String): Unit
}

trait Animal extends Talking with Eating

class Dog extends Animal {

  override def eat(what: String): Unit = {
    println(s"Dog is eating = ${what}")
  }

  override def say: Unit = {
    println("Hau hau hau")
  }
}

class Cat extends Animal {
  override def eat(what: String): Unit = {
    println(s"Cat is eating = ${what}")
  }

  override def say: Unit = {
    println("Miau miau miau")
  }
}

object MultipleTraits extends App {

  val dog = new Dog()
  val cat = new Cat()

  dog.eat("Meat")
  dog.say
  cat.eat("Fish")
  cat.say

  // testing reflections
  dog.getClass.getInterfaces.foreach {
    println
  }
  cat.getClass.getMethods.foreach {
    println
  }

}
