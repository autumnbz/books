// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Person(val name: String = "", val age: Int = 0) {
  println("Just constructed another person")
  def description = name + " is " + age + " years old"
}

val p1 = new Person
val p2 = new Person("Fred") 
val p3 = new Person("Fred", 42) 
p1.description
p2.description
p3.description

import java.util.Properties
import java.io.FileReader

class MyProg {
  private val props = new Properties
  props.load(new FileReader("myprog.properties"))
    // The statement above is a part of the primary constructor
}

class Person(val name: String, private var age: Int) {
  def description = name + " is " + age + " years old"
  def birthday() { age += 1 }
}

val p = new Person("Fred", 42) 
p.name
p.age // Error--it's private
p.birthday()
p.description


