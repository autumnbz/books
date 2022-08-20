// Compile with scalac -classpath /path/to/javaee-jars/\* Credentials.scala
// For example, with GlassFish 3.1, /path/to/javaee-jars is 
// /path/to/glassfish/modules/

import scala.reflect.BeanProperty
import javax.persistence.Entity
import javax.persistence.Id

@Entity class Credentials {
  @Id @BeanProperty var username : String = _
  @BeanProperty var password : String = _
}
