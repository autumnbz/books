// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val a = Array(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)

for (i <- 0 until a.length)
  println(i + ": " + a(i))

0 until a.length

0 until (a.length, 2)

(0 until a.length).reverse

for (elem <- a)
  println(elem)

for (i <- a.indices)
  println(i + ": " + a(i))
