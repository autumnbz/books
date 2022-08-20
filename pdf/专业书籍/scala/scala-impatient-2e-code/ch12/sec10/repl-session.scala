// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

def until(condition: => Boolean)(block: => Unit) {
  if (!condition) {
    block
    until(condition)(block)
  }
}

def indexOf(str: String, ch: Char): Int = {
  var i = 0
  until (i == str.length) {
    if (str(i) == ch) return i
    i += 1
  }
  -1
}

indexOf("Hello", 'l')

indexOf("Hello", '!')

def indexOf(str: String, ch: Char): Int = {
  var i = 0
  try {
    until (i == str.length) {
      if (str(i) == ch) return i
      i += 1
    }
  } catch {
    case t: Throwable => println(t)
  }
  -1
}

indexOf("Hello", 'l')

indexOf("Hello", '!')
