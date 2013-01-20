package vaadin.scala.tests

import vaadin.scala._
import converter.{ DateToLongConverter, Converter }
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import java.util.Locale

@RunWith(classOf[JUnitRunner])
class ConverterTests extends FunSuite {

  class MyConverter extends Converter[String, Date] {
    override def convertToModel(value: Option[String], locale: Locale): Option[Date] = {
      Some(new Date)
    }
    override def convertToPresentation(value: Option[Date], locale: Locale): Option[String] = {
      Some("")
    }
  }

  test("modelType") {
    val myConverter = new MyConverter
    assert(myConverter.modelType === classOf[Date])
  }

  test("p.getModelType") {
    val myConverter = new MyConverter
    assert(myConverter.p.getModelType === classOf[Date])
  }

  test("presentationType") {
    val myConverter = new MyConverter
    assert(myConverter.presentationType === classOf[String])
  }

  test("p.getPresentationType") {
    val myConverter = new MyConverter
    assert(myConverter.p.getPresentationType === classOf[String])
  }

  val locale = Locale.UK

  test("DateToLongConverter") {
    val converter = new DateToLongConverter

    assert(converter.presentationType === classOf[java.util.Date])
    assert(converter.modelType === classOf[java.lang.Long])

    assert(converter.convertToModel(None, locale) === None)
    assert(converter.convertToPresentation(None, locale) === None)

    assert(converter.convertToModel(Some(new Date(1358627734477L)), locale) === Some(1358627734477L))
    assert(converter.convertToPresentation(Some(1358627734477L), locale) === Some(new Date(1358627734477L)))
  }

}