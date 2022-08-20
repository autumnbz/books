// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.xml._
import scala.xml.transform._

val rule1 = new RewriteRule {
  override def transform(n: Node) = n match {
    case e @ <ul>{_*}</ul> => e.asInstanceOf[Elem].copy(label = "ol")
    case _ => n
  }
}

val root = <html><head><title>A List</title></head><body><ul>
  <li>Fred</li><li>Wilma</li></ul></body></html>

val transformed = new RuleTransformer(rule1).transform(root)

val rule2 = new RewriteRule {
  override def transform(n: Node) = n match {
    case Text("Fred") => Text("Frog")
    case _ => n
  }
}

val transformed = new RuleTransformer(rule1, rule2).transform(root)
