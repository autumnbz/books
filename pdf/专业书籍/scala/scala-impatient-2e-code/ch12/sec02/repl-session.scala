// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

(x: Double) => 3 * x

val triple = (x: Double) => 3 * x

Array(3.14, 1.42, 2.0).map(triple)

Array(3.14, 1.42, 2.0).map((x: Double) => 3 * x)

Array(3.14, 1.42, 2.0) map { (x: Double) => 3 * x }
