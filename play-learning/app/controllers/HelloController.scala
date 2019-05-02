package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ActionBuilder, AnyContent, ControllerComponents, Request}

@Singleton
class HelloController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def hello = Action {
    Ok(views.html.h())
  }
}
