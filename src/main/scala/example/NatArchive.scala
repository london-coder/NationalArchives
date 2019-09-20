package example

import scala.io.Source
/*
 * this is the layout of the CSV file, that needs to be input and parsed
 * filename, origin, metadata, hash
 * file1, London, "a file about London", e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6
 * ... 
*/

final case class RawData(filename: String, origin: String, metadata: String, hash: String)

object NatArchive extends Messages with App {

  def dataReader(source: String): Seq[RawData] = {
    for {
      line <- Source.fromFile(source).getLines().drop(1).toList
      values = line.split(",").map(_.trim)
    } yield RawData(values(0), values(1), values(2), values(3))
  }

}

trait Messages {
  lazy val greeting: String = "Welcome to the National Archive file ingester."
}
