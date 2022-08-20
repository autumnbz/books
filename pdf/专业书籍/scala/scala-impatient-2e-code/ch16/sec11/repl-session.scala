// These are meant to be typed into the REPL. You can also run
// scala -Xnojline < repl-session.scala to run them all at once.

import scala.xml._
val root = XML.loadFile("myfile.xml")

import scala.io.Source
import java.io._
import java.net._
val root2 = XML.load(new FileInputStream("myfile.xml"))
val root3 = XML.load(new InputStreamReader(
    new FileInputStream("myfile.xml"), "UTF-8"))
// val root4 = XML.load(new URL("http://horstmann.com/index.html"))
  // This may take a while--see Catalog.scala for more information

// The ConstructingParser preserves white space and comments
import xml.parsing.ConstructingParser
import java.io.File
val parser = ConstructingParser.fromFile(new File("myfile.xml"), preserveWS = true)
val root = parser.document.docElem


val parser = ConstructingParser.fromSource(Source.fromURL("http://horstmann.com/index.html"), preserveWS = false)

val doc = parser.document
val dtd = doc.dtd
val docRoot = doc.docElem

// Note the <!-- unknown entity auml; -->

// Add entity declarations to resolve entities

val parser = ConstructingParser.fromSource(Source.fromURL("http://horstmann.com/index.html"), preserveWS = false)

import scala.xml.dtd._

parser.ent ++= List(
  "nbsp" -> ParsedEntityDecl("nbsp", IntDef("\u00A0")),
  "auml" -> ParsedEntityDecl("auml", IntDef("\u00E4")))

parser.document

// Or use the XHTML parser

import scala.xml.parsing._

val parser = new XhtmlParser(Source.fromURL("http://horstmann.com/index.html"))
val doc = parser.initialize.document

// Saving

XML.save("myfile.xhtml", doc.docElem,
         enc = "UTF-8",
         xmlDecl = true,
         doctype = DocType("html",
                           PublicID("-//W3C//DTD XHTML 1.0 Strict//EN",
                                    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"),
                           Nil))

// Pretty-printing

val printer = new PrettyPrinter(width = 100, step = 4)
val str = printer.formatNodes(root)
