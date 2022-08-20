// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.collection.JavaConverters._
// Converts Java Properties to a Scala map—just to get an interesting example
for ((k, v) <- System.getProperties.asScala)
  println(k + " -> " + v)

for ((k, "") <- System.getProperties.asScala)
  println(k)

for ((k, v) <- System.getProperties.asScala if v == "") 
  println(k)
