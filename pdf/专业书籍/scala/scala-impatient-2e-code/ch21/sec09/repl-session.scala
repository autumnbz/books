// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
  (it.head, it.last)

firstLast(List(1, 7, 2, 9))

firstLast("Fred")

implicitly[String <:< Iterable[_]]

implicitly[String <:< AnyRef]

implicitly[AnyRef <:< String]
