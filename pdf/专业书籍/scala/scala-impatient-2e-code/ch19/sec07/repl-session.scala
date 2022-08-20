// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores: String Map Int = Map("Fred" -> 42)

type ×[A, B] = (A, B)

val pair: String × Int = ("Fred", 42)

val triple: String × Int × Int = (("Fred", 42), 1729)
