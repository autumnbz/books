// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def mul(x: Int, y: Int) = x * y

def mulOneAtATime(x: Int) = (y: Int) => x * y

mulOneAtATime(6)(7)

def mulOneAtATime(x: Int)(y: Int) = x * y

val a = Array("Hello", "World")
val b = Array("hello", "world")
a.corresponds(b)(_.equalsIgnoreCase(_))


