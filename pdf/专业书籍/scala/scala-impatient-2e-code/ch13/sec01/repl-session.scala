// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.


val coll = Array(1, 7, 2, 9) // some Iterable
val iter = coll.iterator
while (iter.hasNext)
  println(iter.next())

import java.awt.Color

Iterable(0xFF, 0xFF00, 0xFF0000)
Set(Color.RED, Color.GREEN, Color.BLUE)
Map(Color.RED -> 0xFF0000, Color.GREEN -> 0xFF00, Color.BLUE -> 0xFF)

import scala.collection._

SortedSet("Hello", "World")
