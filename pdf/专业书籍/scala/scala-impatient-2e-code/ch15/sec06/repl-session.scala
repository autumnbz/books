// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def sum(xs: Seq[Int]): BigInt =
  if (xs.isEmpty) 0 else xs.head + sum(xs.tail)

def sum2(xs: Seq[Int], partial: BigInt): BigInt =
  if (xs.isEmpty) partial else sum2(xs.tail, xs.head + partial)

sum(1 to 1000000) // Will get stack overflow
 
sum2(1 to 1000000, 0) // OK

import scala.annotation._

// The following will give an error message since the
// recursive call is not in tail position
@tailrec def sum(xs: Seq[Int]): BigInt =
  if (xs.isEmpty) 0 else xs.head + sum(xs.tail)

// This is OK
@tailrec def sum2(xs: Seq[Int], partial: BigInt): BigInt =
  if (xs.isEmpty) partial else sum2(xs.tail, xs.head + partial)

class Util {
  // The following will give an error message since the method
  // can be overridden
  @tailrec def sum2(xs: Seq[Int], partial: BigInt): BigInt =
    if (xs.isEmpty) partial else sum2(xs.tail, xs.head + partial)
}

val n = 2

// This is OK
(n: @switch) match {
  case 0 => "Zero"
  case 1 => "One"
  case _ => "?"
}

// This will give an error indicating that a jump table can't be generated
(n: @switch) match {
  case v: Int => "" + v
  case s: String => s
  case _ => "?"
}


