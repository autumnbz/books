// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

// Change the element

val list = <ul><li>Fred</li><li>Wilma</li></ul>
val list2 = list.copy(label = "ol")

// Add a child

list.copy(child = list.child ++ <li>Another item</li>)

// Add or change an attribute

import scala.xml._

val image = <img src="hamster.jpg"/>
val image2 = image % Attribute(null, "alt", "An image of a hamster", Null)

// Add multiple attributes

val image3 = image % Attribute(null, "alt", "An image of a frog",
Attribute(null, "src", "frog.jpg", Null))
