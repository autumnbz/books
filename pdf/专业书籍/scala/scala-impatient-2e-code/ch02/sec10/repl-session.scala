// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def box(s : String) { // Look carefully: no =
  val border = "-" * s.length + "--\n"
  println("\n" + border + "|" + s + "|\n" + border)
}

box("Fred")

box("Wilma")
