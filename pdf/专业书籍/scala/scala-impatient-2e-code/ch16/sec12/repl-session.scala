// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.xml._

def namespaces(node: Node) = {
  def namespaces(scope: NamespaceBinding): List[(String, String)] =
    if (scope == null) List()
    else namespaces(scope.parent) :+ ((scope.prefix, scope.uri))
  namespaces(node.scope)
}

val root = XML.load("sample.xhtml")

namespaces(root)

val title = (root \\ "title")(0)
title.attributes.prefixedKey
title.attributes.key

val scope = new NamespaceBinding("svg", "http://www.w3.org/2000/svg", TopScope)
val attrs = Attribute(null, "width", "100",
  Attribute(null, "height", "100", Null))
val elem = Elem(null, "body", Null, TopScope,
  Elem("svg", "svg", attrs, scope))
