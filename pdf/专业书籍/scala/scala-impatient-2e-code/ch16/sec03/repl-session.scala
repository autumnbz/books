// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val elem = <a href="http://scala-lang.org">The Scala language</a>
val url = elem.attributes("href")
val url = elem.attributes("href").text
val url = elem.attributes.get("href").getOrElse(Text(""))

val image = <img alt="San Jos&eacute; State University Logo"
src="http://www.sjsu.edu/publicaffairs/pics/sjsu_logo_color_web.jpg"/>
val alt = image.attributes("alt")

for (attr <- elem.attributes)
  println(attr.key + "=" + attr.value.text)

val image = <img alt="TODO" src="hamster.jpg"/>
val map = image.attributes.asAttrMap 
