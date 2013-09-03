package main.java.search
import org.scala_tools.time.Imports._

trait Hourly extends Search
{
	def search(date : DateTime) : List[String]
}