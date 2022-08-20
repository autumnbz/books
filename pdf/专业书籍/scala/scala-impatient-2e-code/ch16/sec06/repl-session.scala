// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import xml._

val js = <script><![CDATA[if (temp < 0) alert("Cold!")]]></script>

val code = """if (temp < 0) alert("Cold!")"""
val js = <script>{PCData(code)}</script>

val n1 = <xml:unparsed><&></xml:unparsed>
val n2 = Unparsed("<&>")

val g1 = <xml:group><li>Item 1</li><li>Item 2</li></xml:group>
val g2 = Group(Seq(<li>Item 1</li>, <li>Item 2</li>))

val items = <li>Item 1</li><li>Item 2</li>
for (n <- <xml:group>{items}</xml:group>) yield n
  // Yields two li elements
for (n <- <ol>{items}</ol>) yield n
  // Yields one ol element
