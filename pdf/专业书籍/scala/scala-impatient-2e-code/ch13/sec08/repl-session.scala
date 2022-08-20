// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val names = List("Peter", "Paul", "Mary")

names.map(_.toUpperCase) // List("PETER", "PAUL", "MARY")

for (n <- names) yield n.toUpperCase

def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())

names.map(ulcase)
names.flatMap(ulcase)

"-3+4".collect { case '+' => 1 ; case '-' => -1 }

names.foreach(println)


