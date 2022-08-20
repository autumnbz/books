import scala.util.parsing.combinator._
import scala.util.parsing.input.CharSequenceReader
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

object Main extends App {
  class OnesParser extends RegexParsers {    
    def ones: Parser[Any] = "1" ~ ones | "1"
  }

  class OnesPackratParser extends RegexParsers with PackratParsers {
    lazy val ones: PackratParser[Any] = ones ~ "1" | "1"
    def parseAll[T](p: Parser[T], input: String) =
      phrase(p)(new PackratReader(new CharSequenceReader(input)))
  }
  
  val parser = new OnesParser
  val input = "11111"
  println(parser.parseAll(parser.ones, input))

  val pparser = new OnesPackratParser
  println(pparser.parseAll(pparser.ones, input))
}
