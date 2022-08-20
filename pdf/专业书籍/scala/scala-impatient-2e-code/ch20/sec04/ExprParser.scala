import scala.util.parsing.combinator._

class ExprParser extends RegexParsers {
  val number = "[0-9]+".r

  // Can't discard the "+" or "-" since they are needed in the match
  def expr: Parser[Int] = term ~ opt(("+" | "-") ~ expr) ^^ {
    case t ~ None => t
    case t ~ Some("+" ~ e) => t + e
    case t ~ Some("-" ~ e) => t - e
  }  

  def term: Parser[Int] = factor ~ rep("*" ~> factor) ^^ {
    case f ~ r => f * r.product
  }

  def factor: Parser[Int] = number ^^ { _.toInt } | "(" ~> expr <~ ")" 
}

object Main extends App {
  val parser = new ExprParser
  val result = parser.parseAll(parser.expr, "3-4*5")
  if (result.successful) println(result.get)
}
