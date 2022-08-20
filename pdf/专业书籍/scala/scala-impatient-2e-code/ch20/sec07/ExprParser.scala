import scala.util.parsing.combinator._

class ExprParser extends RegexParsers {
  val number = "[0-9]+".r

  def expr: Parser[Int] = term ~ rep(
    ("+" | "-") ~ term ^^ {
      case "+" ~ t => t
      case "-" ~ t => -t
    }) ^^ { case t ~ r => t + r.sum }

  def term: Parser[Int] = factor into { first =>
    rep("*" ~> factor) ^^ { first * _.product }
  }

  def factor: Parser[Int] = log(number)("number") ^^ { _.toInt } | "(" ~> expr <~ ")" 
}

object Main extends App {
  val parser = new ExprParser
  val result = parser.parseAll(parser.expr, "3-4*5")
  if (result.successful) println(result.get)
}
