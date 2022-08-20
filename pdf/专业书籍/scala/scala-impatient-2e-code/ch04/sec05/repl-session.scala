// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val scores = scala.collection.immutable.SortedMap("Alice" -> 10,
  "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)

val months = scala.collection.mutable.LinkedHashMap("January" -> 1,
  "February" -> 2, "March" -> 3, "April" -> 4, "May" -> 5, 
  "June" -> 6, "July" -> 7, "August" -> 8, "September" -> 9,
  "October" -> 10, "November" -> 11, "December" -> 12)

// Contrast with a sorted map

scala.collection.mutable.SortedMap("January" -> 1,
  "February" -> 2, "March" -> 3, "April" -> 4, "May" -> 5, 
  "June" -> 6, "July" -> 7, "August" -> 8, "September" -> 9,
  "October" -> 10, "November" -> 11, "December" -> 12)

// or a hash map

Map("January" -> 1,
  "February" -> 2, "March" -> 3, "April" -> 4, "May" -> 5, 
  "June" -> 6, "July" -> 7, "August" -> 8, "September" -> 9,
  "October" -> 10, "November" -> 11, "December" -> 12)

