package scala.vaadin.scala.tests
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.FunSuite
import vaadin.scala._
import scala.collection.JavaConversions._
import com.vaadin.data.util.IndexedContainer

class ContainerTests extends FunSuite {

  test("property creation with a string") {
    val result = Property("foobar")
    assert(classOf[String] === result.getType)
    assert("foobar" === result.getValue)
  }

  test("property creation with a symbol") {
    val result = Property('foobar)
    assert(classOf[Symbol] === result.getType)
    assert('foobar === result.getValue)
  }

  test("item creation with one property") {
    val result = Item('testId -> "foobar")
    assert(1 === result.getItemPropertyIds.size)
    val property = result.getItemProperty('testId)
    assert(classOf[String] === property.getType)
    assert("foobar" === property.getValue)
  }

  test("item creation with three properties") {
    val result = Item('testId1 -> "foobar1", 'testId2 -> "foobar2", 'testId3 -> "foobar3")
    assert(3 === result.getItemPropertyIds.size)
    for (propertyId <- result.getItemPropertyIds) {
      val property = result.getItemProperty(propertyId)
      assert(classOf[String] === property.getType)
      assert(true === property.getValue.asInstanceOf[String].startsWith("foobar"))
    }
  }

  test("container creation with one item and one property") {
    val result = Container('itemId -> List('propertyId -> "foobar"))

    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(1 === item.getItemPropertyIds.size)
    val property = item.getItemProperty('propertyId)
    assert(classOf[String] === property.getType)
    assert("foobar" === property.getValue)
  }

  test("container creation with one item") {
    val result = Container('itemId -> List())
    assert(1 === result.size)
    val item = result.getItem('itemId)
    assert(0 === item.getItemPropertyIds.size)
  }

  test("container item id filter with one item") {
    val containerWithOneItem = Container('itemId -> List())
    val result = containerWithOneItem \ 'itemId item

    assert(null != result)
  }

  test("container item filter with one item") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterItems (_.getItemPropertyIds.contains('propertyId)) items

    assert(1 === result.size)
  }

  test("container property id filter with one item and property") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem \\ 'propertyId properties

    assert(1 === result.size)
    assert("value" === result.head.getValue)
  }

  test("container property filter with one item and property") {
    val containerWithOneItem = Container('itemId -> List('propertyId -> "value"))
    val result = containerWithOneItem filterProperties (_.getValue() == "value") properties

    assert(1 === result.size)
    assert("value" === result.head.getValue)
  }

  test("item property id filter with two properties") {
    val itemWithOneProperty = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty \ 'propertyId1

    assert("value1" === result.getValue)
  }

  test("item property filter with two properties") {
    val itemWithOneProperty = Item('propertyId1 -> "value1", 'propertyId2 -> "value2")
    val result = itemWithOneProperty filterProperties (_.getValue() == "value1") properties

    assert(1 === result.size)
    assert("value1" === result.head.getValue)
  }
}