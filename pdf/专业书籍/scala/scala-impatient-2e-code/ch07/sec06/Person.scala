package com.horstmann.impatient

package object people {
  val defaultName = "John Q. Public"
}

package people {
  class Person {
    var name = defaultName // A constant from the package
    private[impatient] def description = "A person with name " + name
  }
}

// Run as scala com.horstmann.impatient.Main

object Main extends App {
  val john = new com.horstmann.impatient.people.Person
  println(john.description)
}
