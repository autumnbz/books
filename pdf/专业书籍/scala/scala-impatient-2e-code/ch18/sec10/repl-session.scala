// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Person(name: String) {
  override def toString = getClass.getName + " " + name
}

class Student(name: String) extends Person(name)

val length = 10
val students = new Array[Student](length)
val people: Array[Person] = students // Not legal, but suppose it was . . .
// Let's force it, so you can see what happens
val people: Array[Person] = students.asInstanceOf[Array[Person]]
people(0) = new Person("Fred") // Oh no! Now students(0) isn’t a Student

val people = new Array[Person](length)
val students: Array[Student] = people // Not legal, but suppose it was . . .
// Let's force it, so you can see what happens
val students: Array[Student] = people.asInstanceOf[Array[Student]]

// You cannot define a covariant mutable pair

class Pair[+T](var first: T, var second: T) // Error

// This pair is immutable
class Pair[+T](val first: T, val second: T) {
  // But newFirst is in a contravariant position
  def replaceFirst(newFirst: T) = new Pair[T](newFirst, second) // Error
}

class Pair[+T](val first: T, val second: T) {
  // Remedy: Another type parameter
  def replaceFirst[R >: T](newFirst: R) = new Pair[R](newFirst, second)
}


