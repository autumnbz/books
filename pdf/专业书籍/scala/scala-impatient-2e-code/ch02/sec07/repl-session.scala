// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def abs(x: Double) = if (x >= 0) x else -x

println(abs(3))
println(abs(-3))

def fac(n : Int) = {
  var r = 1
  for (i <- 1 to n) r = r * i
  r
}

println(fac(10))

def recursiveFac(n: Int): Int = 
  if (n <= 0) 1 else n * recursiveFac(n - 1)

println(recursiveFac(10))

