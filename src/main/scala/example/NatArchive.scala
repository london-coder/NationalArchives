package example

import scala.io.Source
import java.io.{PrintWriter, File}
import java.nio.file.{Files, Paths, StandardCopyOption}
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
 
  def updateRowValues(csvFileName: String, column: String, oldValue: String, newValue: String): Unit = {
    val data = dataReader(csvFileName)
    val index = columnIndex(data, column)

    val out = iterateOverData(data, index, oldValue, newValue)

    val outputfName = csvFileName + ".modified"
    val sink = new PrintWriter(new File(outputfName))
    dataWriter(sink, out)

    sink.flush
    sink.close
    // renameFile(outputfName, csvFileName)
  }
  
  def dataReader(filename: String): List[Array[String]] = {
    val ds = Source.fromFile(filename, "UTF-8")
    val data = for {
      line <- ds.getLines().toList
      values = line.split(",").map(_.trim) // remove leading and trailing whitespace between columns in the CSV file.
    } yield values
    ds.close
    data
  }
  
  def columnIndex(data: Seq[Array[String]], column: String): Int = 
    data.head.zipWithIndex.toMap.withDefaultValue(0)(column)
  
  // not private as its used in a test
  def iterateOverData(data: Seq[Array[String]], index: Int, oldValue: String, newValue: String): Seq[Array[String]] =
    for {
      line <- data
    } yield line match {
      case m if m(index).equalsIgnoreCase(oldValue) => swapValue(m, index, newValue)
      case m => m
  }
  
  private def dataWriter(out: PrintWriter, data: Seq[Array[String]]): Unit =
    for (l <- data) {
      out.println(l.mkString(", "))
    }
  
  private def swapValue(line: Array[String], index: Int, correct: String): Array[String] = {
    line(index) = correct
    line.toArray
  }
  
  def duplicateFiles(data: List[Array[String]], groupingColumn: Int, duplicateColumn: Int): Map[String, List[String]] =
    data.groupMap(_(groupingColumn))(_(duplicateColumn)) filter (_._2.size > 1)
  
  def displayContent(data: Map[String, List[String]]): Unit = {
    data foreach { x =>
      Console.out.println(s"HASH = ${x._1}")
      x._2 foreach { y =>
        Console.out.println(s"File Names = $y")
      }
    }
  }
  
  def renameFile(outputfName: String, csvFileName: String): Unit = {
    val path = Files.move(Paths.get(outputfName), Paths.get(csvFileName), StandardCopyOption.REPLACE_EXISTING)
    if (path != null)  Console.out.println("Modified file has been renamed.")
    else Console.err.println(s"ERROR: failed to rename file $outputfName to $csvFileName")
  }

}
