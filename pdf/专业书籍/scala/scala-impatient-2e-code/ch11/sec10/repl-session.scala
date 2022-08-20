// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

object Name {
  def unapplySeq(input: String): Option[Seq[String]] =
    if (input.trim == "") None else Some(input.trim.split("\\s+"))
}

val author = "Peter van der Linden"

author match {
  case Name(first, last) => author
  case Name(first, middle, last) => first + " " + last
  case Name(first, "van", "der", last) => "Hello Peter!"
}
