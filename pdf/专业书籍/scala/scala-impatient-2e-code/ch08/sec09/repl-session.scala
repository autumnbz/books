// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

abstract class Person {
  val id: Int 
    // No initializer—this is an abstract field with an abstract getter method
  var name: String 
    // Another abstract field, with abstract getter and setter methods
  override def toString = getClass.getName + "[id=" + id + ",name=" + name + "]"
}

class Employee(val id: Int) extends Person { // Subclass has concrete id property
  var name = "" // and concrete name property
}

val james = new Employee(7)

val fred = new Person {
  val id = 1729
  var name = "Fred"
}



