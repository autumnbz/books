// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Pair[T <% Comparable[T]](val first: T, val second: T) {
  def smaller = if (first.compareTo(second) < 0) first else second
  override def toString = "(" + first + "," + second + ")"
}

val p = new Pair(4, 2) // Works
p.smaller

class Pair[T](val first: T, val second: T)(implicit ev: T => Comparable[T]) {
  def smaller = if (first.compareTo(second) < 0) first else second
  override def toString = "(" + first + "," + second + ")"
}

val p = new Pair(4, 2) // Works
p.smaller

