import scala.annotation.strictfp
import scala.collection.mutable.HashMap
import scala.math._

class Fred {
  @volatile var done = false
    // Becomes a volatile field in the JVM
  @transient var recentLookups = new HashMap[String, String]
    // Becomes a transient field in the JVM
  @strictfp def calculate(x: Double) = sqrt(x)
  @native def win32RegKeys(root: Int, path: String): Array[String] = null
}

/*

$ javap -private Fred
Compiled from "Fred.scala"
public class Fred implements scala.ScalaObject {
  private volatile boolean done;
  private transient scala.collection.mutable.HashMap<java.lang.String, java.lang.String> recentLookups;
  public boolean done();
  public void done_$eq(boolean);
  public scala.collection.mutable.HashMap<java.lang.String, java.lang.String> recentLookups();
  public void recentLookups_$eq(scala.collection.mutable.HashMap<java.lang.String, java.lang.String>);
  public strictfp double calculate(double);
  public native java.lang.String[] win32RegKeys(int, java.lang.String);
  public Fred();
}

*/
