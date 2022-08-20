// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

val scores = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

val scores = new scala.collection.mutable.HashMap[String, Int]

"Alice" -> 10

val scores = Map(("Alice", 10), ("Bob", 3), ("Cindy", 8))
