// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import java.nio.file._

val dirname = ".."
val entries = Files.walk(Paths.get(dirname)) // or Files.list
try {
  entries.forEach(p => println(p))
} finally {
  entries.close()
}

