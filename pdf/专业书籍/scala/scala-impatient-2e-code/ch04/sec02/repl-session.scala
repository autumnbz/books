// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

val bobsScore = scores("Bob")

val fredsScore = if (scores.contains("Fred")) scores("Fred") else 0

scores.getOrElse("Bob", 0)

scores.get("Bob")
scores.get("Fred")
