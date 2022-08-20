import java.awt._
import java.io._
import javax.swing._
import scala.collection.mutable._

object Main extends App {
  val image = new ArrayBuffer[Shape with Serializable { def contains(p: Point): Boolean } ]
  image += new Rectangle(5, 10, 20, 30)
  val poly = new Polygon
  poly.addPoint(40, 40)
  poly.addPoint(40, 70)
  poly.addPoint(70, 40)
  image += poly

  val frame = new JFrame
  frame.add(new JComponent {
    override def paintComponent(g: Graphics) {
      val graphics = g.asInstanceOf[Graphics2D]
      for (s <- image) {
        if (s.contains(new Point(50, 50)))
          graphics.fill(s)
        else
          graphics.draw(s)
      }
    }
    override def getPreferredSize = new Dimension(100, 100)
  })
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.pack()
  frame.setVisible(true)

  val out = new ObjectOutputStream(new FileOutputStream("image.ser"))
  out.writeObject(image)
  out.close()
}
