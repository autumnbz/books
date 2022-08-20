// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.annotation._

:paste

@implicitNotFound(msg = "I am baffled why you give me ${From} when I want ${To}.")
abstract class <:<[-From, +To] extends Function1[From, To]

object <:< {
  implicit def conforms[A] = new (A <:< A) { def apply(x: A) = x }
}

def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
  (it.head, it.last)

firstLast("Fred")


