package main.java.search
import org.scala_tools.time.Imports._

class Searcher(override val item: String, override val pool:String) extends Search

trait Search
{
	val item : String
	val pool : String
}

trait Daily extends Search
{
	def searchForDay(date : DateTime)
}

trait Hourly extends Search
{
	def searchForHour(date : DateTime)
}