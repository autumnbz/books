class Document {
  private var title = ""
  private var author = "" 
  // These methods return this.type so they can be used
  // for method chaining, even with subclasses
  def setTitle(title: String): this.type = { this.title = title; this }
  def setAuthor(author: String): this.type = { this.author = author; this }
  override def toString = getClass.getName + "[title=" + title + ",author=" + author + "]"
}

class Book extends Document {
  private var chapters = new scala.collection.mutable.ArrayBuffer[String]
  def addChapter(chapter: String) = { chapters += chapter; this }
  override def toString = super.toString + "[chapters=" + chapters + "]"
}

object Main extends App {
  val book = new Book
  book.setTitle("Scala for the Impatient").addChapter("Chapter 1 ...") 
  println(book)
}
