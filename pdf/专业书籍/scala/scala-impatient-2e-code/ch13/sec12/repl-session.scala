// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)

val tenOrMore = numsFrom(10)

tenOrMore.tail.tail.tail

val squares = numsFrom(1).map(x => x * x)

squares.take(5).force

import scala.io.Source

val words = Source.fromFile("/usr/share/dict/words").getLines.toStream
words 
words(5) 
words 

