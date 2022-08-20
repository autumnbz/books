/*

Compile and run as
  
scalac -classpath .:/path/to/junit/junit-4.x.jar MyTest.scala 
scala -classpath .:/path/to/junit/junit-4.x.jar org.junit.runner.JUnitCore MyTest

*/

import org.junit._
import org.junit.Assert._

class MyTest {
  @Test(timeout = 100) def testSomeFeature() {
    assertTrue(6 * 7 == 42)
  }
}
