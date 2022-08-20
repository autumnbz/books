// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val arr = Array(0, 1)

arr match {
  case Array(0, x) => x
}

Array.unapplySeq(arr)

val pattern = "([0-9]+) ([a-z]+)".r

"99 bottles" match {
  case pattern(num, item) => (num.toInt, item)
}

pattern.unapplySeq("99 bottles")
