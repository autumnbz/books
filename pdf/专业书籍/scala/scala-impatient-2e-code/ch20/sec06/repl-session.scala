// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.util.parsing.combinator._

class OnesParser extends RegexParsers {
  def ones: Parser[Any] = ones ~ "1" | "1" 
  // The following work:
  // def ones: Parser[Any] = "1" ~ ones | "1"
  // def ones: Parser[Any] = "1" ~ rep("1")
}

val parser = new OnesParser()
parser.parseAll(parser.ones, "111")


