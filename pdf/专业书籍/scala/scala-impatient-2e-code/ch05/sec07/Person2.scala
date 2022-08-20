class Person2(name: String, age: Int) {
  val description = name + " is " + age + " years old"
}

/*

$ javap -private Person2
Compiled from "Person2.scala"
public class Person2 implements scala.ScalaObject {
  private final java.lang.String description;
  public java.lang.String description();
  public Person2(java.lang.String, int);
}
  
*/
