// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Message {
  val timeStamp = new java.util.Date
}

val msg = new Message
msg.timeStamp

class Counter {
  private var value = 0
  def increment() { value += 1 }
  def current = value // No () in declaration
}

val myCounter = new Counter
myCounter.current
