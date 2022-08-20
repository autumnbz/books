// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Pair[T : Ordering](val first: T, val second: T) {
  def smaller(implicit ord: Ordering[T]) =
    if (ord.compare(first, second) < 0) first else second
  override def toString = "(" + first + "," + second + ")"
}

val p = new Pair(4, 2)
p.smaller

val q = new Pair("Fred", "Brooks")
q.smaller

