package example

import org.scalatest._

class NatArchiveSpec extends FlatSpec with Matchers {
  "The NatArchive object" should "identify as" in {
    NatArchive.greeting shouldEqual "Welcome to the National Archive file ingester."
  }

  "The file reader" should "read in rows of data" in {
    NatArchive.dataReader("src/resources/data.csv").size shouldEqual 4
  } 
}
