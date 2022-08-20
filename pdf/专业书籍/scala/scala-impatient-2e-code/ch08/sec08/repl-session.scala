// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

abstract class Person(val name: String) {
  def id: Int // No method body—this is an abstract method
}

class Employee(name: String) extends Person(name) {
  def id = name.hashCode // override keyword not required
}

val fred = new Employee("Fred")
fred.id
