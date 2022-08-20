import scala.annotation._

class Processor {
  @varargs def process(args: String*) {
    println(args.mkString("<", "|", ">"))
  }
}

object Main extends App {
  new Processor().process("Mary", "had", "a", "little", "lamb")
}

/*

$ scalac Processor.scala 
$ scala Main
<Mary|had|a|little|lamb>
$ javac -classpath .:/path/to/scala/lib/scala-library.jar ProcessorClient.java
$ java -classpath .:/path/to/scala/lib/scala-library.jar ProcessorClient
<Mary|had|a|little|lamb>
$ javap Processor
Compiled from "Processor.scala"
public class Processor implements scala.ScalaObject {
  public void process(java.lang.String...);
  public void process(scala.collection.Seq<java.lang.String>);
  public Processor();
}

*/


