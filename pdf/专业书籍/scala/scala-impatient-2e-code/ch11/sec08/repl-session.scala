// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

object Name {
  def unapply(input: String) = {
    val pos = input.indexOf(" ")
    if (pos == -1) None
    else Some((input.substring(0, pos), input.substring(pos + 1)))
  }
}

val author = "Cay Horstmann"

val Name(first, last) = author // calls Name.unapply(author)
first
last

Name.unapply(author)
Name.unapply("Anonymous")

case class Currency(value: Double, unit: String)
  // See Chapter 14 for case classes
val amt = Currency(29.95, "EUR")
amt match {
  case Currency(amount, "USD") => println("$" + amount)
  case Currency(amount, "EUR") => println("€" + amount)
  case _ => println(amt)
}





