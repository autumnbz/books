// To get the serialVersionUID, run
// serialver -classpath .:/path/to/scala/lib/scala-library.jar Employee

import java.util.Date

@cloneable @remote @SerialVersionUID(6157032470129070425L)
class Employee(var name: String, var salary: Double) extends Serializable {
  var hireDay = new Date
  override def clone = {
    val cloned = super.clone.asInstanceOf[Employee]
    cloned.hireDay = hireDay.clone.asInstanceOf[Date]
    cloned
  }
}

/*

Note the following:
* The static field serialVersionUID has been generated
* All methods are tagged to throw a RemoteException

$ javap -private Employee
Compiled from "Employee.scala"
public class Employee implements scala.Serializable,scala.ScalaObject,java.lang.Cloneable,java.rmi.Remote {
  public static final long serialVersionUID;
  private java.lang.String name;
  private double salary;
  private java.util.Date hireDay;
  public static {};
  public java.lang.String name() throws java.rmi.RemoteException;
  public void name_$eq(java.lang.String) throws java.rmi.RemoteException;
  public double salary() throws java.rmi.RemoteException;
  public void salary_$eq(double) throws java.rmi.RemoteException;
  public java.util.Date hireDay() throws java.rmi.RemoteException;
  public void hireDay_$eq(java.util.Date) throws java.rmi.RemoteException;
  public Employee clone() throws java.rmi.RemoteException;
  public java.lang.Object clone() throws java.rmi.RemoteException;
  public Employee(java.lang.String, double) throws java.rmi.RemoteException;
}

*/

