// Version 1: The event source is an Object

import scala.collection.mutable.ArrayBuffer
import java.awt.event.ActionEvent

trait Listener[E] {
  def occurred(e: E): Unit
}

trait Source[E, L <: Listener[E]] {
  private val listeners = new ArrayBuffer[L]
  def add(l: L) { listeners += l }
  def remove(l: L) { listeners -= l }
  def fire(e: E) {
    for (l <- listeners) l.occurred(e)
  }
}

trait ActionListener extends Listener[ActionEvent]

class Button extends Source[ActionEvent, ActionListener] {
  def click() {
    fire(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "click"))
  }
}

object Main extends App {
  val b = new Button
  b.add(new ActionListener {
    def occurred(e: ActionEvent) {
      println(e)
    }
  })
  b.click()
}
