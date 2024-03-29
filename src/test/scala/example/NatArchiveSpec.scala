package example

import org.scalatest._
import java.security.MessageDigest
import java.nio.file.{Paths, Files}

class NatArchiveSpec extends FlatSpec with Matchers {

  // reused values
  val fName = "src/resources/data.csv"

  val fileData = NatArchive.dataReader(fName)

  "The file reader" should "read in all lines from the file" in {
    fileData.size shouldEqual 5
  }

  "The requested column of the data" should "have an index of 1 from the header row" in {
    NatArchive.columnIndex(fileData, "origin") shouldEqual 1
  }

  "The original data" should "differ from the corrected data" in {
    val input = NatArchive.dataReader(fName)
    val inputStr = input.mkString
    val inputChecksum =
      new String(MessageDigest.getInstance("MD5").digest(inputStr.getBytes))

    val changed = NatArchive.iterateOverData(input, 1, "Londom", "London")
    val changedStr = changed.mkString
    val changedChecksum =
      new String(MessageDigest.getInstance("MD5").digest(changedStr.getBytes))

    inputChecksum should not equal changedChecksum
  }

  "Running the updateRowValues function" should "write a new file with the faulty value changed" in {
    NatArchive.updateRowValues(fName, "origin", "Londom", "London")
    val correctedFile = Files.exists(Paths.get(fName + ".modified"))
    // scala.reflect.io.File(fName+".modified").exists shouldBe true
    correctedFile shouldBe true
  }

  "If there are duplicates" should "return the list of duplicate file names" in {
    val data = NatArchive.dataReader(fName)
    val hashColumn = 3
    val fileColumn = 0
    val dups = NatArchive.duplicateFiles(data, hashColumn, fileColumn)
    dups.size shouldEqual 1
    // visibility of the duplicates
    NatArchive.displayContent(dups)
  }
}
