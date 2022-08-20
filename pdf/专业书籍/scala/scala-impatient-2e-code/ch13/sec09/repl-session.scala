// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

List(1, 7, 2, 9).reduceLeft(_ - _)

List(1, 7, 2, 9).reduceRight(_ - _)

List(1, 7, 2, 9).foldLeft(0)(_ - _)

(0 /: List(1, 7, 2, 9))(_ - _)

val freq = scala.collection.mutable.Map[Char, Int]()
for (c <- "Mississippi") freq(c) = freq.getOrElse(c, 0) + 1

(Map[Char, Int]() /: "Mississippi") {
  (m, c) => m + (c -> (m.getOrElse(c, 0) + 1))
}

(1 to 10).scanLeft(0)(_ + _)
