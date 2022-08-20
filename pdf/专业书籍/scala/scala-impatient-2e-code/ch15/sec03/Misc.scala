// Compile with scalac -classpath .:/path/to/javaee-jars/\*:/path/to/junit/junit-4.x.jar Misc.scala

import java.io._
import javax.inject.Named
import javax.persistence.Entity
import org.junit.Test
import org.junit.Assert._

@Entity class Credentials // This annotation has no arguments

class MyTest {
  @Named("creds") var credentials: Credentials = _
    // This annotation has a "value" argument

  @Test(timeout = 100, expected = classOf[IOException]) 
    // This annotation has two arguments
  def testNonexistentFile() {     
    new FileReader("/fred.txt")
  }

  @Test 
    // Same as @Test(timeout = 0, expected = classOf[org.junit.Test.None])
  def testSomeFeature() { 
    assertTrue(6 * 7 == 42)
  }

  def show(@deprecatedName('sep) separator: String = ", ") {}
    // This annotation has an argument of type Symbol 
    // In Scala, annotation arguments can have any type
}

