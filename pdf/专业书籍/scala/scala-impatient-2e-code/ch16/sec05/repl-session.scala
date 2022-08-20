// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.xml._

def makeURL(f: String) = "http://horstmann.com/" + f

val fileName = "hamster.jpg"

<img src={makeURL(fileName)}/>

<img src="{makeURL(fileName)}"/>

<a id={new Atom(1)}/>

val description = "TODO"

<img alt={if (description == "TODO") null else description} src="hamster.jpg"/>

<img alt={if (description == "TODO") None else Some(Text(description))} src="hamster.jpg"/>

