// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

for (i <- 1 to 3; j <- 1 to 3) print((10 * i + j) + " ")

for (i <- 1 to 3; j <- 1 to 3 if i != j) print((10 * i + j) + " ")

for (i <- 1 to 3; from = 4 - i; j <- from to 3) print((10 * i + j) + " ")
for { i <- 1 to 3
     from = 4 - i
     j <- from to 3 }
  print((10 * i + j) + " ")

for (i <- 1 to 10) yield i % 3

for (c <- "Hello"; i <- 0 to 1) yield (c + i).toChar

for (i <- 0 to 1; c <- "Hello") yield (c + i).toChar
