// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

Vector(1, 2, 3) :+ 5
1 +: Vector(1, 2, 3)

import scala.collection.mutable.ArrayBuffer

val numbers = ArrayBuffer(1, 2, 3)
numbers += 5

var numberSet = Set(1, 2, 3)
numberSet += 5 // Sets numberSet to the immutable set numberSet + 5
numberSet
var numberVector = Vector(1, 2, 3)
numberVector :+= 5 // += does not work since vectors don't have a + operator
numberVector

Set(1, 2, 3) - 2

numbers ++ Vector(1, 2, 7, 9)

numbers -- Vector(1, 2, 7, 9)
