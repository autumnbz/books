// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val prices = List(5.0, 20.0, 9.95)
val quantities = List(10, 2, 1)

prices zip quantities

(prices zip quantities) map { p => p._1 * p._2 }

((prices zip quantities) map { p => p._1 * p._2 }) sum

List(5.0, 20.0, 9.95) zip List(10, 2)

List(5.0, 20.0, 9.95).zipAll(List(10, 2), 0.0, 1)

"Scala".zipWithIndex

"Scala".zipWithIndex.max

"Scala".zipWithIndex.max._2


