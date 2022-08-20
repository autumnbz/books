// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.math._

val palindromicSquares = (1 to 1000000).view.map(x => { println(x) ; x * x }).filter(x => x.toString == x.toString.reverse)

// Evaluates the first eleven
palindromicSquares.take(10).mkString(",")
palindromicSquares.take(10).mkString(",")


// Contrast with streams

def squares(x: Int): Stream[Int] = { println(x) ; x * x } #:: squares(x + 1)

val palindromicSquareStream = squares(0).filter(x => x.toString == x.toString.reverse)

palindromicSquareStream(10)
palindromicSquareStream(10)

// Caution

// Evaluates only the first ten
palindromicSquares.take(10).last

// Evaluates all elements
palindromicSquares(10)
