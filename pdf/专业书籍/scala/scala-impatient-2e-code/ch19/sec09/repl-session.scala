// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

// A method of the REPL object
def square(x: Int) = x * x

// Turned into a function

square _

// A function

val triple = (x: Int) => 3 * x
