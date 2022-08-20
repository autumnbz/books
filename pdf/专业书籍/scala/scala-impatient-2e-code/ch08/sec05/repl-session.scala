// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

class Person(val name: String, val age: Int) {
  override def toString = getClass.getName + "[name=" + name +
    ",age=" + age + "]"
}

class Employee(name: String, age: Int, val salary : Double) extends
  Person(name, age) {
  override def toString = super.toString + "[salary=" + salary + "]"
}

new Employee("Fred", 42, 50000)

import java.io._
import java.nio.charset._
import java.nio.file._

class PathWriter(p: Path, cs: Charset) extends
    java.io.PrintWriter(Files.newBufferedWriter(p, cs))

val out = new PathWriter(Paths.get("out.txt"), StandardCharsets.UTF_8)
out.println("Hello, World!")
out.println(42)
out.close()


