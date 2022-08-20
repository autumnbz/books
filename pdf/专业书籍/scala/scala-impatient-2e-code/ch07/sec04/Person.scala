package com.horstmann.impatient
package people

class Person {
  val cars = new scala.collection.mutable.ArrayBuffer[Car]
    // Car is in the package com.horstmann.impatient
  def description = "A person with " + cars.length + " cars"    
}

// Run as scala com.horstmann.impatient.people.Main

object Main extends App {
  val fred = new Person
  fred.cars += new Car 
  fred.cars += new Car
  println(fred.description)
}  

