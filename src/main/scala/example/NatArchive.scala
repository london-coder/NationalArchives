package example

import scala.io.{Source, BufferedSource}
import scala.collection.mutable.ArrayBuffer
import java.io.{PrintWriter, File}

/*
 * this is the layout of the CSV file, that needs to be input and parsed
 * filename, origin, metadata, hash
 * file1, London, "a file about London", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
 * ... 
 * An easy way to do this, is using an awk script eg
 * awk -F ',' '{gsub("Londom","London",$2); print}' OFS="," data.csv > adjusted-data.csv
 * but this is not what is being asked for.  
 */


object NatArchive extends App {
/*  Program entry point
if(args.length == 4 ) {
  // parse the command line parameters to pass to updateRowValues(args(0), args(1),...)
} else {
  Console.err.pritln("Insuffiecient number of parameters...")
}
*/
  def updateRowValues(csvFileName: String, column: String, oldValue: String, newValue: String): Unit = {
    val index = fields(column.toLowerCase())
    val source = Source.fromFile(csvFileName, "UTF-8")
    val sink = new PrintWriter(new File(csvFileName+".modified"))

    val data = dataReader(source)

    val out = iterateOverData (data, index, oldValue, newValue)

    dataWriter(sink, out)

    source.close
    sink.flush
    sink.close
    // what should happen here, but is not implemented, to maintain visibility of the change, is to 
    // rename the 'corrected' file to the original filename
  }

  def fields = Map[String, Int] ("filename" -> 0, "origin" -> 1, "metadata" -> 2, "hash" -> 3)

  def dataReader(source: BufferedSource): Seq[Array[String]] = 
    for {
      line <- source.getLines().toSeq
      values = line.split(",").map(_.trim) // remove leading and trailing whitespace between columns in the CSV file.
    } yield values 


  // not private as its used in a test
  def iterateOverData(data: Seq[Array[String]], index: Int, oldValue: String, newValue: String): Seq[Array[String]] =
    for {
      line <- data
    } yield line match {
      case m if m(index).equalsIgnoreCase(oldValue) => swapValues(m, index, newValue)
      case m => m
    }

  private def dataWriter (out: PrintWriter, data: Seq[Array[String]] ): Unit = 
    for( l <- data ) {
      out.println(l.mkString(","))
    }

  private def swapValues(line: Array[String], index: Int, correct: String): Array[String] = {
    line(index) = correct
    line.toArray
  }

}
