// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val list = <dl><dt>Java</dt><dd>Gosling</dd><dt>Scala</dt><dd>Odersky</dd></dl>
val languages = list \ "dt"

val doc = <html><head><title>Lists and an image</title></head><body>
  <ul><li>Fred</li><li>Wilma</li></ul>
  <ul><li>Java</li><li>Scala</li></ul>
  <ul><li>Frog</li><li>Hamster
  <img src="hamster.jpg" alt="image of a hamster"/></li></ul>
</body></html>

doc \ "body" \ "_" \ "li"

val img = doc \\ "img"
img \ "@alt"

doc \\ "@alt"

for (n <- doc \\ "img") println(n)

(<img src="hamster.jpg"/><img src="frog.jpg"/> \\ "@src").text
