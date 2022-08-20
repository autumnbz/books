// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val elem = <a href="http://scala-lang.org">The <em>Scala</em> language</a>

for (n <- elem.child) {
  println(n.getClass.getName + ": " + n)
}

val elem = <p><!-- a comment -->)&anEntityRef;<?a processing instruction ?></p>

for (n <- elem.child) {
  println(n.getClass.getName + ": " + n)
}

import scala.xml._

val items = new NodeBuffer
items += <li>Fred</li>
items += <li>Wilma</li>
val nodes: NodeSeq = items
