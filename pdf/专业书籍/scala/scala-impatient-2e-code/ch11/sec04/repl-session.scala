// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

var a: Any = 3
a ->= 4 // Same as a = a -> 4
a

a === 4 // Error; not the same as a = a == 4 because == starts with =

val b = scala.collection.mutable.Set(1, 2, 3)

b += 4 // Calls the += method; you couldn't use b = b + 4 with a val



