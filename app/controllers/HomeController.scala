package controllers

import javax.inject.Inject
import play.api.mvc._

/**
  * The classic WidgetController using MessagesAbstractController.
  *
  * Instead of MessagesAbstractController, you can use the I18nSupport trait,
  * which provides implicits that create a Messages instance from a request
  * using implicit conversion.
  *
  * See https://www.playframework.com/documentation/2.8.x/ScalaForms#passing-messagesprovider-to-form-helpers
  * for details.
  */
class HomeController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  val checkboxCountName = "checkboxCount"
  def index() = Action { implicit request: Request[AnyContent] =>
    val checkboxCount = getCount(request)
    println(checkboxCount)
    Ok(views.html.index(checkboxCount))
  }

  def testForPost() = Action(parse.multipartFormData) {
    implicit request: Request[
      MultipartFormData[play.api.libs.Files.TemporaryFile]
    ] =>
      Redirect(controllers.routes.HomeController.index)
  }

  private def getCount(request: Request[_]) = {
    request.getQueryString(checkboxCountName).map(_.toInt).getOrElse(10000)
  }
}
