// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)

for ((k, v) <- scores) println(k + " is mapped to " + v)

scores.keySet

for (v <- scores.values) println(v)

for ((k, v) <- scores) yield (v, k)
