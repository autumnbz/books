// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.


val vec = (1 to 1000000) map (_ % 100)
  // map transforms a Range into a Vector
val lst = vec.toList

def time[T](block: => T) = {
  val start = System.nanoTime
  val result = block
  val elapsed = System.nanoTime - start
  println(elapsed + " nanoseconds")
  result
}

time(vec(500000))

time(lst(500000))
