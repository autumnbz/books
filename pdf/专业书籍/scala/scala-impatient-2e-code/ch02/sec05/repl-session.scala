// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

var r = 1
var n = 10

while (n > 0) {
  r = r * n
  n -= 1
}

println(r)

r = 1
n = 10

for (i <- 1 to n)
  r = r * i

println(r)

val s = "Hello"
var sum = 0
for (i <- 0 to s.length - 1) 
  sum += s(i)

println(sum)

sum = 0
for (ch <- "Hello") sum += ch

import scala.util.control.Breaks._
breakable {
  for (ch <- "Hello World") {
    if (ch == ' ') break; // Exits the breakable block
    println(ch)
  }
}
