import scala.io._
import java.io._

class Book {
  var text = ""

  @throws(classOf[IOException]) 
  def read(filename: String) { 
    val source = Source.fromFile(filename, "UTF-8")
    text = source.mkString
    source.close()
  }  
}

/*

Comment out the @throws annotation and compile BookClient.java:

$ scalac Book.scala
$ java -classpath .:/path/to/scala/lib/scala-library.jar BookClient.java
BookClient.java:9: error: exception IOException is never thrown in body of corresponding try statement
      } catch(IOException ex) {
        ^
*/
