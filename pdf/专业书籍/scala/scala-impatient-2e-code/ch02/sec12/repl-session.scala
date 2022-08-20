// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.math._

def root(x: Double) = 
  if (x >= 0) { sqrt(x) 
  } else throw new IllegalArgumentException("x should not be negative")

try {
  println(root(4))
  println(root(-4))
} catch {
  case e: Exception => println(e)
}


import java.net._
import javax.imageio._
import javax.swing._

def process(u: URL) {
  val img = ImageIO.read(u)
  JOptionPane.showMessageDialog(null, null, null, 0, new ImageIcon(img))
}

try { 
  process(new URL("http://horstmann.com/cay-tiny.gif"))
  process(new URL("http://horstmann.com/fred.gif"))
} catch {
  case _: MalformedURLException => println("Bad URL: " + url)
  case ex: IOException => ex.printStackTrace()
}

try { 
  process(new URL("fred://horstmann.com/cay-tiny.gif"))
} catch {
  case _: MalformedURLException => println("Bad URL: " + url)
  case ex: IOException => ex.printStackTrace()
}

// -- Change to input stream

import java.io._

def process(in: InputStream) {
  var bytes = -1
  var next = 0
  while (next != -1) {
    next = in.read()
    bytes += 1
  }
  println(bytes + " bytes")
}

val in = new URL("http://horstmann.com/cay-tiny.gif").openStream()
try {
  process(in)
} finally {
  in.close()
}

// try/catch/finally is no good--what if close throws an exception?

in = new URL("http://horstmann.com/cay-tiny.gif").openStream()
try { 
  process(in)
} catch { 
  case ex: IOException => println(ex)
} finally { 
  in.close()
}

// This is what you want

try {
  in = new URL("http://horstmann.com/cay-tiny.gif").openStream()
  try {
    process(in)
  } finally { 
    in.close()
  }
} catch { 
  case ex: IOException => println(ex)
}

import scala.util._
import scala.io._
val result =
  for (a <- Try { StdIn.readLine("a: ").toInt };
      b <- Try { StdIn.readLine("b: ").toInt })
    yield a / b
