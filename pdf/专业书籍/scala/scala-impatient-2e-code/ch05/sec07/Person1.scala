class Person1(name: String, age: Int) {
  def description = name + " is " + age + " years old"
}

/*

$ javap -private Person1
Compiled from "Person1.scala"
public class Person1 implements scala.ScalaObject {
  private final java.lang.String name;
  private final int age;
  public java.lang.String description();
  public Person1(java.lang.String, int);
}

*/
