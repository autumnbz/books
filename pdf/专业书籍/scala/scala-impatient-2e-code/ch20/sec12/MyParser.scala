import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.combinator.token.StdTokens
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.matching.Regex

/* In this example, I parse a part of a mythical language whose strings
 * are enclosed in single quotes and whose character constants
 * have the form #dd..d where each d is a decimal digit.
 */

trait MyTokens extends StdTokens { 
  case class CharLit(chars: String) extends Token
}

class MyLexical extends StdLexical with MyTokens {
  def regex(r: Regex): Parser[String] = new Parser[String] {
    def apply(in: Input) = r.findPrefixMatchOf(
      in.source.subSequence(in.offset, in.source.length)) match {
        case Some(matched) =>
          Success(in.source.subSequence(in.offset,
            in.offset + matched.end).toString, in.drop(matched.end))
        case None => Failure("string matching regex `" + r +
          "' expected but " + in.first + " found", in)
      }
  }

  override def token: Parser[Token] = 
    regex("[a-zA-Z][a-zA-Z0-9]*".r) ^^ { processIdent(_) } |
    regex("0|[1-9][0-9]*".r) ^^ { NumericLit(_) } |
    regex("'([^']|'')*'".r) ^^ { StringLit(_) } |
    regex("#[0-9]+".r) ^^ { s: String => CharLit(s.drop(1).toInt.toChar.toString) } |
    delim                         
}

class MyParser extends StdTokenParsers {
  type Tokens = MyTokens
  val lexical = new MyLexical

  def parseAll[T](p: Parser[T], in: String): ParseResult[T] =
    phrase(p)(new lexical.Scanner(in))

  def charLit: Parser[String] = 
    accept("character literal", { case t: lexical.CharLit => t.chars })  

  lexical.reserved += ("boolean", "char", "int", "var", "float", "true", "false", "string")
  lexical.delimiters += (":", "=", ";")

  def vardecl: Parser[Any] = ("var" ~> repsep(ident, ",") <~ ":") ~ typ ~ opt(init) <~ ";"
  def typ = "int" | "float" | "char" | "string" | "boolean"
  def init: Parser[Any] = "=" ~ (numericLit | stringLit | charLit | "false" | "true")
}

object Main extends App {
  val parser = new MyParser
  println(parser.parseAll(parser.vardecl, "var c: int = 100;").get)
  println(parser.parseAll(parser.vardecl, "var str: string = 'Hello';").get)
  println(parser.parseAll(parser.vardecl, "var c: char = #100;").get)
}
