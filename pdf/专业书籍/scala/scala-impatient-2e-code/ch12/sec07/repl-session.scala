// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import java.awt.event._
import javax.swing._

var counter = 0

val button = new JButton("Increment")

// Old fashioned way
button.addActionListener(new ActionListener {
  override def actionPerformed(event: ActionEvent) {
    counter += 1
  }
})

// Scala function becomes Java SAM type
button.addActionListener(event => counter += 1)

// Note that we now have two listeners attached, each of
// whom increments the counter

val listener: ActionListener = event => println(counter)
button.addActionListener(listener)

val exit = (event: ActionEvent) => if (counter > 9) System.exit(0)
button.addActionListener(exit(_))

val frame = new JFrame
frame.add(button)
frame.pack()
frame.setVisible(true)


