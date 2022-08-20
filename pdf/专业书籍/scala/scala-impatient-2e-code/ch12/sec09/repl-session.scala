// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def runInThread(block: () => Unit) {
  new Thread {
    override def run() { block() }
  }.start()
}

runInThread { () => println("Hi") ; Thread.sleep(10000); println("Bye") }

def runInThread(block: => Unit) {
  new Thread {
    override def run() { block }
  }.start()
}

runInThread { println("Hi") ; Thread.sleep(1000); println("Bye") }

def until(condition: => Boolean)(block: => Unit) {
  if (!condition) {
    block
    until(condition)(block)
  }
}

var x = 10
until (x == 0) {
  x -= 1
  println(x)
}

Thread.sleep(10000)
