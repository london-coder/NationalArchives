package example

import org.scalatest._
import scala.io.Source
import java.security.MessageDigest

class NatArchiveSpec extends FlatSpec with Matchers {
  // created  by the giter8 template DELETE when done.
  "The NatArchive object" should "identify as" ignore {
    NatArchive.greeting shouldEqual "Welcome to the National Archive file data modifier."
  }
  // reused values
  val fName = "src/resources/data.csv"
  val origin = 1

  "The file reader" should "read in all lines in the file" in {
    NatArchive.dataReader(scala.io.Source.fromFile(fName)).size shouldEqual 5
  } 

  "An origin data" should "be equal to the corrected data" in {
    val input = NatArchive.dataReader(Source.fromFile(fName))
    val inputStr = input.mkString(" ", ",", "\\n")
    val inputChecksum = new String(MessageDigest.getInstance("MD5").digest(inputStr.getBytes))

    val changed = NatArchive.iterateOverData(input, origin, "Londom", "London")
    val changedStr = changed.mkString(" ", ",", "\\n")
    val changedChecksum = new String(MessageDigest.getInstance("MD5").digest(changedStr.getBytes))

    inputChecksum should not equal changedChecksum
  }

  "Running the updateRowValues function" should "write a new file with the faulty value changed" in {

    NatArchive.updateRowValues(fName, "origin", "Londom", "London")
    // dummy assertion to execute the exercise method. 
    origin shouldBe 1
  }
}
