// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.collection.JavaConverters._

val props = System.getProperties.asScala

val propsMap = mapAsScalaMap(System.getProperties)

props("com.horstmann.scala") = "impatient"

System.getProperty("com.horstmann.scala")


