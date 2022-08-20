// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val a = Array(2, 3, 5, 7, 11)
val result = for (elem <- a) yield 2 * elem

for (elem <- a if elem % 2 == 0) yield 2 * elem

import scala.collection.mutable.ArrayBuffer
def removeAllNegatives(a: ArrayBuffer[Int]) = {
  var n = a.length
  var i = 0
  while (i < n) {
    if (a(i) >= 0) i += 1
    else { a.remove(i); n -= 1 }
  }
}

val b = ArrayBuffer(1, -2, -3, 4, -5)
removeAllNegatives(b)
b

val big = new ArrayBuffer[Int]
big ++= Range(-200000,  200000)
val start = System.nanoTime
removeAllNegatives(big)
println(f"${(System.nanoTime - start) * 1E-9}%.2f seconds")
big.length

def removeAllNegatives2(a: ArrayBuffer[Int]) = {
  val positionsToRemove = for (i <- a.indices if a(i) < 0) yield i
  for (i <- positionsToRemove.reverse) a.remove(i)
}

val b = ArrayBuffer(1, -2, -3, 4, -5)
removeAllNegatives2(b)
b

val big = new ArrayBuffer[Int]
big ++= Range(-200000,  200000)
val start = System.nanoTime
removeAllNegatives2(big)
println(f"${(System.nanoTime - start) * 1E-9}%.2f seconds")
big.length

def removeAllNegatives3(a: ArrayBuffer[Int]) = {
  val positionsToKeep = for (i <- a.indices if a(i) >= 0) yield i
  for (j <- positionsToKeep.indices) a(j) = a(positionsToKeep(j))
  a.trimEnd(a.length - positionsToKeep.length)
}

val b = ArrayBuffer(1, -2, -3, 4, -5)
removeAllNegatives3(b)
b

val big = new ArrayBuffer[Int]
big ++= Range(-200000,  200000)
val start = System.nanoTime
removeAllNegatives3(big)
println(f"${(System.nanoTime - start) * 1E-9}%.2f seconds")
big.length

