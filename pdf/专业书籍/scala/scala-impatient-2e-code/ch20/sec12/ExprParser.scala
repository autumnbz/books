import scala.util.parsing.combinator.syntactical._

class ExprParser extends StandardTokenParsers {
  lexical.delimiters += ("+", "-", "*", "(", ")")
  def expr: Parser[Any] = term ~ rep(("+" | "-") ~ term)
  def term: Parser[Any] = factor ~ rep("*" ~> factor)
  def factor: Parser[Any] = numericLit | "(" ~> expr <~ ")"
  def parseAll[T](p: Parser[T], in: String): ParseResult[T] =
    phrase(p)(new lexical.Scanner(in))
}

object Main extends App {
  val parser = new ExprParser
  val result = parser.parseAll(parser.expr, "(3+4)*5")
  if (result.successful) println(result.get)
}
