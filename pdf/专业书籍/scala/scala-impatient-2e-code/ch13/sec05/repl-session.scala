// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

Set(2, 0, 1) + 1
Set(2, 0, 1) + 4

for (i <- Set(1, 2, 3, 4, 5, 6)) print(i + " ")

val weekdays = scala.collection.mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")

collection.immutable.SortedSet(1, 2, 3, 4, 5, 6)

val digits = Set(1, 7, 2, 9)
digits contains 0 // false
Set(1, 2) subsetOf digits // true

val primes = Set(2, 3, 5, 7)

digits union primes

digits & primes

digits -- primes
