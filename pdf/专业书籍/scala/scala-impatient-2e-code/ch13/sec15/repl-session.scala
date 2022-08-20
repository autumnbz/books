// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val coll = (1 to 1000000).map(BigInt(_))

coll.par.sum

coll.par.count(_ % 2 == 0)

for (i <- (0 until 100).par) print(i + " ")

for (i <- (0 until 100).par) yield i + " "

// Don't do this:

var count = 0
for (c <- coll.par) { if (c % 2 == 0) count += 1 }
count


// Associative fold and aggregate

val str = (1 to 1000).par.map(_.toString).fold("")(_ + _)
str.par.aggregate(Set[Char]())(_ + _, _ ++ _)
