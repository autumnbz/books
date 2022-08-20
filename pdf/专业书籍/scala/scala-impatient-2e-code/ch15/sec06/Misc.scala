import scala.annotation._
import scala.annotation.elidable._

class Misc {
  
  @elidable(FINE) def dump(props: Map[String, String]) {}

  def makeMap(keys: Seq[String], values: Seq[String]) = {
    assert(keys.length == values.length, "lengths don't match")
    (keys zip values).toMap
  }

  def allDifferent[@specialized T](x: T, y: T, z: T) = x != y && x != z && y != z
}

object Main extends App {
  println(new Misc().makeMap(Array("Fred", "Wilma"), Array("Scala")))
}

/*

Note the ten versions of allDifferent:

$ scalac Misc.scala 
$ javap Misc
Compiled from "Misc.scala"
public class Misc implements scala.ScalaObject {
  public void dump(scala.collection.immutable.Map<java.lang.String, java.lang.String>);
  public scala.collection.immutable.Map<java.lang.String, java.lang.String> makeMap(scala.collection.Seq<java.lang.String>, scala.collection.Seq<java.lang.String>);
  public <T extends java/lang/Object> boolean allDifferent(T, T, T);
  public boolean allDifferent$mVc$sp(scala.runtime.BoxedUnit, scala.runtime.BoxedUnit, scala.runtime.BoxedUnit);
  public boolean allDifferent$mZc$sp(boolean, boolean, boolean);
  public boolean allDifferent$mBc$sp(byte, byte, byte);
  public boolean allDifferent$mSc$sp(short, short, short);
  public boolean allDifferent$mCc$sp(char, char, char);
  public boolean allDifferent$mIc$sp(int, int, int);
  public boolean allDifferent$mJc$sp(long, long, long);
  public boolean allDifferent$mFc$sp(float, float, float);
  public boolean allDifferent$mDc$sp(double, double, double);
  public Misc();
}

The dump method is elided in the following:

$ scalac -Xelide-below INFO Misc.scala
$ javap Misc
Compiled from "Misc.scala"
public class Misc implements scala.ScalaObject {
  public scala.collection.immutable.Map<java.lang.String, java.lang.String> makeMap(scala.collection.Seq<java.lang.String>, scala.collection.Seq<java.lang.String>);
  public <T extends java/lang/Object> boolean allDifferent(T, T, T);
  public boolean allDifferent$mVc$sp(scala.runtime.BoxedUnit, scala.runtime.BoxedUnit, scala.runtime.BoxedUnit);
  public boolean allDifferent$mZc$sp(boolean, boolean, boolean);
  public boolean allDifferent$mBc$sp(byte, byte, byte);
  public boolean allDifferent$mSc$sp(short, short, short);
  public boolean allDifferent$mCc$sp(char, char, char);
  public boolean allDifferent$mIc$sp(int, int, int);
  public boolean allDifferent$mJc$sp(long, long, long);
  public boolean allDifferent$mFc$sp(float, float, float);
  public boolean allDifferent$mDc$sp(double, double, double);
  public Misc();
}

With and without assertions:

$ scala Main
java.lang.AssertionError: assertion failed: lengths don't match
$ scalac -Xelide-below MAXIMUM Misc.scala 
$ scala Main
Map(Fred -> Scala)


*/
