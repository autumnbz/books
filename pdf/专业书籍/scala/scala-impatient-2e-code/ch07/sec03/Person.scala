package com.horstmann.impatient {
  package people {
    class Person(val name: String) {
      val friends = new collection.mutable.ArrayBuffer[Person]
      // Doesn't pick up collection from com.horstmann.collection
      def description = name + " with friends " + 
        friends.map(_.name).mkString(", ")
    }
  }
}


// Run as scala com.horstmann.collection.Main

package com.horstmann.collection {
  object Main extends App {
    val fred = new com.horstmann.impatient.people.Person("Fred")
    val wilma = new com.horstmann.impatient.people.Person("Wilma")
    val barney = new com.horstmann.impatient.people.Person("Barney")
    fred.friends += wilma
    fred.friends += barney
    println(fred.description)
  }  
}
