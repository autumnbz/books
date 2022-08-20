// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import java.awt.Color

Color.RED

import java.awt._

Font.BOLD

import java.awt.Color._
val c1 = RED // Color.RED
val c2 = decode("#ff0000")

import java.awt._

def handler(evt: event.ActionEvent) { // java.awt.event.ActionEvent
  println(evt)
  System.exit(0)
}

val frame = new javax.swing.JFrame
val button = new javax.swing.JButton("Exit")
frame.add(button)
button.addActionListener(new event.ActionListener {
  override def actionPerformed(evt: event.ActionEvent) {
    handler(evt)
  }
})
frame.pack
frame.setVisible(true)
