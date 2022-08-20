// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

case class Currency(value: Double, unit: String)

val amt = Currency(1000.0, "EUR")

amt match { case a Currency u => a + " " + u }

val lst = List(1, 7, 2, 9)
lst match { 
  case h :: t => h + t.length 
  case _ => 0
} 

lst match { 
  case ::(h, t) => h + t.length 
  case _ => 0
}  

// ~ matches parse results

val parser = new scala.util.parsing.combinator.RegexParsers {
  val amount = "[0-9]+".r ~ "[A-Z]{3}".r

  def parse(in: String) = {
    val result = parseAll(amount, in).get
    result match { 
      case p ~ q => Currency(p.toDouble, q) // same as case ~(p, q)
    }    
  }
}

parser.parse("1000 EUR")

// :: is right-associative

List(1, 7, 2, 9) match { 
  case first :: second :: rest => first + second + rest.length 
  case _ => 0
}

List(1, 7, 2, 9) match { 
  case ::(first, ::(second, rest)) => first + second + rest.length 
  case _ => 0
}

List(List(1, 7), List(2, 9)) match { 
  case (first :: second) :: rest => first + second.length + rest.length 
  case _ => 0
}

// Infix notation works with any binary unapply--doesn't have to 
// come from case class

case object +: {
  def unapply[T](input: List[T]) = 
    if (input.isEmpty) None else Some((input.head, input.tail))
}

1 +: 7 +: 2 +: 9 +: Nil match { 
  case first +: second +: rest => first + second + rest.length 
}
