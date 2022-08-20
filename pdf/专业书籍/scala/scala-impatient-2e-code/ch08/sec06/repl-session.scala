// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Person(val name: String) {
  override def toString = getClass.getName + "[name=" + name + "]"
}

class SecretAgent(codename: String) extends Person(codename) {
  override val name = "secret" // Don’t want to reveal name . . .
  override val toString = "secret" // . . . or class name
}

val fred = new Person("Fred")
fred.name
val james = new SecretAgent("007")
james.name

// It is more common to override an abstract def with a val

abstract class Person { // See Section 8.8 for abstract classes
  def id: Int // Each person has an ID that is computed in some way  
}

class Student(override val id: Int) extends Person

class SecretAgent extends Person {
  override val id = scala.util.Random.nextInt
}

val fred = new Student(1729)
fred.id
val james = new SecretAgent
james.id
