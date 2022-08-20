// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.math._
val num = 3.14
val fun = ceil _

fun(num)

Array(3.14, 1.42, 2.0).map(fun)

