// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.collection.mutable.ArrayBuffer

val b = ArrayBuffer[Int]()
val b2 = new ArrayBuffer[Int] // If you use new, the () is optional

b += 1
b += (1, 2, 3, 5)
b ++= Array(8, 13, 21)

b.trimEnd(5)
b

b.insert(2, 6)
b

b.insert(2, 7, 8, 9)
b

b.remove(2)
b

b.remove(2, 3)
b

val a = b.toArray
a.toBuffer
