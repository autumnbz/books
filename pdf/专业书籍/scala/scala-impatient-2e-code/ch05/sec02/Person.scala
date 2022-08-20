class Person {
  var age = 0
}

/*

$ javap -private Person
Compiled from "Person.scala"
public class Person implements scala.ScalaObject {
  private int age;
  public int age();
  public void age_$eq(int);
  public Person();
}

*/
