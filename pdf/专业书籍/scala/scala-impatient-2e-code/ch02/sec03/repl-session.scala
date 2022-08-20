// These are meant to be typed into the REPL. You can also run
// scala -Xnojline -Xnojline < repl-session.scala to run them all at once.

val x0 = 1.0
val y0 = 1.0
var x = 4.0
var y = 5.0

import scala.math._

val distance = { val dx = x - x0; val dy = y - y0; sqrt(dx * dx + dy * dy) }

var r = 1
var n = 10
{ r = r * n; n -= 1 } // Has value Unit

x = y = 1 // No--can't assign Unit to x
