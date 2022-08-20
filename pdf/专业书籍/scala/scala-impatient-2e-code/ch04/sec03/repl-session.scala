// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

scores("Bob") = 10
scores("Fred") = 7

scores

val scores = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

scores += ("Bob" -> 10, "Fred" -> 7)

scores -= "Alice"

val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

val newScores = scores + ("Bob" -> 10, "Fred" -> 7) // New map with update

var scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
scores = scores + ("Bob" -> 10, "Fred" -> 7)

scores -= "Alice"
scores

