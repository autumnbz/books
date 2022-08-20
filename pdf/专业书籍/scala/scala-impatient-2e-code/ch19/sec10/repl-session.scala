// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

trait Logged {
  def log(msg: String)
}

trait LoggedException extends Logged {
  this: Exception =>
    def log() { log(getMessage()) }
  // OK to call getMessage because this is an Exception
}

import javax.swing._

val f = new JFrame with LoggedException // Error
// JFrame isn’t a subtype of Exception, the self type of LoggedException

trait ManagedException extends LoggedException { // Error
  // Self type doesn't inherit
  def print() { println(getMessage()) }
}

trait ManagedException extends LoggedException {
  this: Exception => // Must repeat self type
    def print() { println(getMessage()) }
}



