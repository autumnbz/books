// Version 2: Using a self type for the event source

import scala.collection.mutable.ArrayBuffer
import java.awt.event.ActionEvent

trait Event[S] {
  var source: S = _
}

trait Listener[S, E <: Event[S]] {
  def occurred(e: E): Unit
}

trait Source[S, E <: Event[S], L <: Listener[S, E]] {
  this: S =>
    private val listeners = new ArrayBuffer[L]
  def add(l: L) { listeners += l }
  def remove(l: L) { listeners -= l }
  def fire(e: E) {
    e.source = this // Self-type needed here
    for (l <- listeners) l.occurred(e)
  }
}

class ButtonEvent extends Event[Button]

trait ButtonListener extends Listener[Button, ButtonEvent]

class Button extends Source[Button, ButtonEvent, ButtonListener] {
  def click() { fire(new ButtonEvent) }
}

object Main extends App {
  val b = new Button
  b.add(new ButtonListener {
    def occurred(e: ButtonEvent) {
      println(e + " from " + e.source)
    }
  })
  b.click()
}
