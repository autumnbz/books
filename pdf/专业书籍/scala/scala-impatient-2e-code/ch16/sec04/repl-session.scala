// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.xml._

val items = Array("Fred", "Wilma")
val lst = <ul><li>{items(0)}</li><li>{items(1)}</li></ul>

val containsAtoms = <p>{42}{"Fred"}{Text("Wilma")}</p>
for (n <- containsAtoms.child) {
  println(n.getClass.getName + ": " + n)
}

val lst = <ul>{for (i <- items) yield <li>{i}</li>}</ul>

<h1>The Natural Numbers {{1, 2, 3, ...}}</h1>
