// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val str = "+-3!"
for (i <- str.indices) {
  var sign = 0
  var digit = 0

  str(i) match {
    case '+' => sign = 1
    case '-' => sign = -1
    case ch if Character.isDigit(ch) => digit = Character.digit(ch, 10)
    case _ => 
  }

  println(str(i) + " " + sign + " " + digit)
}

import scala.math._
val x = random
x match {
  case Pi => "It's Pi"
  case _ => "It's not Pi"
}

import java.io.File._
str match {
  case `pathSeparator` => "It's the path separator"
  case _ => "It's not the path separator"
}

