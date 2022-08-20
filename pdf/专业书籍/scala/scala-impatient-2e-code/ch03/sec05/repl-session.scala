// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.collection.mutable.ArrayBuffer

Array(1, 7, 2, 9).sum

ArrayBuffer("Mary", "had", "a", "little", "lamb").max

val b = ArrayBuffer(1, 7, 2, 9)
val bSorted = b.sorted

val a = Array(1, 7, 2, 9)
scala.util.Sorting.quickSort(a)

a.mkString(" and ")

a.mkString("<", ",", ">")

a.toString

b.toString


