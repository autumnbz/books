// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

val doc = <html><head><title>Fred's Memoirs</title></head><body>...</body></html>

val items = <li>Fred</li><li>Wilma</li>

val (x, y) = (1, 2)
x < y // OK
x<y // OK
x <y // Error—unclosed XML literal
