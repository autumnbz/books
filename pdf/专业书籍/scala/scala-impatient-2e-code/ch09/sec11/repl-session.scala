// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val numitemPattern = "([0-9]+) ([a-z]+)".r

for (m <- numitemPattern.findAllMatchIn("99 bottles, 98 bottles"))
  println(m.group(1))

val numitemPattern(num, item) = "99 bottles"

for (numitemPattern(num, item) <- numitemPattern.findAllIn("99 bottles, 98 bottles"))
  println(item + ": " + num)
