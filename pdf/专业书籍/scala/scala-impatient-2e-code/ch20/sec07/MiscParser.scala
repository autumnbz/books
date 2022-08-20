import scala.util.parsing.combinator._

class MiscParser extends RegexParsers {
  val number = "[0-9]+".r
  val ident = "[A-Za-z][A-Za-z0-9]*".r
  def numberList = repsep(number, ",")
  def bounds = rep1("[" ~> number <~ "]")
  def coords = repN(4, number)
  def prod = chainl1(number ^^ { _.toInt }, "*" ^^^ { (x: Int, y: Int) => x * y })
  def fun = guard(ident ~ "(") ~> (ident <~ "(") ~ numberList <~ ")"
}

object Main extends App {
  val parser = new MiscParser
  println(parser.parseAll(parser.numberList, "3,4,5").get)
  println(parser.parseAll(parser.bounds, "[3][4][5]").get)
  println(parser.parseAll(parser.coords, "3 4 5 10").get)
  println(parser.parseAll(parser.prod, "3*4*5").get)
  println(parser.parseAll(parser.fun, "f(3, 4, 5)").get)
}

