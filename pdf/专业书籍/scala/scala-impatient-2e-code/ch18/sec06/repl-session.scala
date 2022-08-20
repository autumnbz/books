// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.reflect._

def makePair[T : ClassTag](first: T, second: T) = {
  val r = new Array[T](2); r(0) = first; r(1) = second; r
} 

val a = makePair(4, 2) // An Array[Int]
a.getClass // In the JVM, [I is an int[] array

val b = makePair("Fred", "Brooks") // An Array[String]
b.getClass // In the JVM, [Ljava.lang.String; is a String[] array


