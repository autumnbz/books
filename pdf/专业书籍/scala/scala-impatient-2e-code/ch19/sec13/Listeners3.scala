// Version 3: Using abstract types

import scala.collection.mutable.ArrayBuffer
import java.awt.event.ActionEvent

trait ListenerSupport {
  type S <: Source
  type E <: Event
  type L <: Listener

  trait Event {
    var source: S = _
  }

  trait Listener {
    def occurred(e: E): Unit
  }

  trait Source {
    this: S =>

    private val listeners = new ArrayBuffer[L]
    def add(l: L) { listeners += l }
    def remove(l: L) { listeners -= l }
    def fire(e: E) {
      e.source = this
      for (l <- listeners) l.occurred(e)
    }
  }
}

object ButtonModule extends ListenerSupport {
  type S = Button
  type E = ButtonEvent
  type L = ButtonListener

  class ButtonEvent extends Event

  trait ButtonListener extends Listener

  class Button extends Source {
    def click() { fire(new ButtonEvent) }
  }
}

object Main extends App {
  import ButtonModule._

  val b = new Button
  b.add(new ButtonListener {
    def occurred(e: ButtonEvent) {
      println(e + " from " + e.source)
    }
  })
  b.click()
}
