// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

// Match a node with no children

val node = <img src="hamster.jpg"/>

node match {
  case <img/> => node \ "@src"
  case <a/> => node \ "@href"
}


// Match a node with one child

val node = <li>Harry</li>

node match {
  case <li>{_}</li> => "It's a li with one child"
}

// Match a node with children

val node = <li>an <em>important</em> item</li>

node match {
  case <li>{_}</li> => "It's a li with one child"
  case <li>{_*}</li> => "It's a li"
}

// Bind the match to a variable

val node = <li>Harry</li>

node match {
  case <li>{child}</li> => child.text
}

import scala.xml._

node match {
  case <li>{Text(item)}</li> => item
}

val node = <li>an <em>important</em> item</li>

node match {
  case <li>{child}</li> => child
  case <li>{children @ _*}</li> => for (c <- children) yield c
}

// Match an attribute

val node = <img src="hamster.jpg" alt="TODO"/>

node match {
  case n @ <img/> if (n.attributes("alt").text == "TODO") => node \ "@src"
}



