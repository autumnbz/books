// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Counter {
  private var value = 0 // You must initialize the field
  def increment() { value += 1 } // Methods are public by default
  def current() = value
}

val myCounter = new Counter
myCounter.increment()
println(myCounter.current)

val myCounter = new Counter() // () ok
myCounter.current() // () ok

class Counter {
  private var value = 0 
  def increment() { value += 1 } 
  def current = value // No () in definition
}

val myCounter = new Counter
myCounter.current() // () not ok
