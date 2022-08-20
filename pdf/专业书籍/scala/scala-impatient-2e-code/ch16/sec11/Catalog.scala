import scala.xml._
import java.net._

object Main extends App {
  System.setProperty("xml.catalog.files", "/etc/xml/catalog")
  // This works on Linux. To install the XHTML DTDs, run
  // sudo apt-get install w3c-dtd-xhtml, then fix up the catalog.xml
  // file as described in https://bugs.launchpad.net/ubuntu/+source/w3c-dtd-xhtml/+bug/400259
  // On Windows or Mac OS X, you need to install the catalog file
  // and DTDs by hand, and point xml.catalog.files to the catalog file

  val res = new com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver

  val loader = new scala.xml.factory.XMLLoader[Elem] {
    override def adapter = new scala.xml.parsing.NoBindingFactoryAdapter() {
      override def resolveEntity(publicId: String, systemId: String) = {
        res.resolveEntity(publicId, systemId) 
      }
    }
  }

  val doc = loader.load(new URL("http://horstmann.com/index.html"))
  println(doc);
}
