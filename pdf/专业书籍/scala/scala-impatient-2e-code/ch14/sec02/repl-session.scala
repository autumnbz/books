// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

for (ch <- "+-3!") {
  var sign = 0
  var digit = 0

  ch match {
    case '+' => sign = 1
    case '-' => sign = -1
    case _ if Character.isDigit(ch) => digit = Character.digit(ch, 10)
    case _ => sign = 0
  }

  println(ch + " " + sign + " " + digit)
}
