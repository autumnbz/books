// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

(1, 3.14, "Fred")

val t = (1, 3.14, "Fred")

val second = t._2

val first = t _1

val (first, second, third) = t

val (first, second, _) = t

"New York".partition(_.isUpper)
