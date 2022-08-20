// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = new scala.collection.mutable.HashMap[String, Int]
scores("Bob") = 100 
scores.update("Bob", 100) 
val bobsScore = scores("Bob")
scores.apply("Bob")
