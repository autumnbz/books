// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Pair[T](val first: T, val second: T) {
  def smaller = if (first.compareTo(second) < 0) first else second // Error
}

class Pair[T <: Comparable[T]](val first: T, val second: T) {
  def smaller = if (first.compareTo(second) < 0) first else second
}

val p = new Pair("Fred", "Brooks")
println(p.smaller)

new Pair(4, 2) // Won't work--see Section 17.4 for a remedy

class Pair[T](val first: T, val second: T) {
  def replaceFirst[R >: T](newFirst: R) = new Pair(newFirst, second)
  override def toString = "(" + first + "," + second + ")"
}

class Person(name: String) {
  override def toString = getClass.getName + " " + name
}

class Student(name: String) extends Person(name)

val fred = new Student("Fred")
val wilma = new Student("Wilma")
val barney = new Person("Barney")

val p = new Pair(fred, wilma)
p.replaceFirst(barney) // A Pair[Person]

// Don't omit the upper bound:

class Pair[T](val first: T, val second: T) {
  def replaceFirst[R](newFirst: R) = new Pair(newFirst, second)
  override def toString = "(" + first + "," + second + ")"
}

val p = new Pair(fred, wilma)
p.replaceFirst(barney) // A Pair[Any]

