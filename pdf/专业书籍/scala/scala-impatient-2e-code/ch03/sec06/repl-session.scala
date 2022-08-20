// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.


import scala.collection.mutable.ArrayBuffer

val a = Array(1, -2, 3, -4, 5)
val b = ArrayBuffer(1, 7, 2, 9)

a.count(_ > 0)

b.append(1, 7, 2, 9)

b.appendAll(a)

b += 4 -= 7

b.copyToArray(a)
a

val xs = Array(1, "Fred", 2, 9)
b.copyToArray(xs)
xs

ArrayBuffer("Mary", "had", "a", "little", "lamb").max

import java.util._

ArrayBuffer(new GregorianCalendar(2012, 0, 1), 
            new GregorianCalendar(1999, 11, 31)).max[Calendar]
// Calendar implements Comparable[Calendar]

b.padTo(20, -1)

(1 to 10).padTo(20, -1) // Note that the result is a Vector, not a Range


