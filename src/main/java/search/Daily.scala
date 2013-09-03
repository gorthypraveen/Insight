package main.java.search
import org.scala_tools.time.Imports._
import scala.collection.mutable.HashMap
import org.joda.time.{ DateTime => JodaDateTime}

trait Daily extends Search
{
	def search(date : DateTime) : List[String] = 
	{
		println(date)
		val start : JodaDateTime = new JodaDateTime(date.year().get(), date.getMonthOfYear().intValue, date.getDayOfMonth().intValue, 0, 0, 0, 0)
		val range = 1 until 9
		for
		{
		   range <- range.toList
		   dataCenter <- availableDCs
		} yield {
		  Thread.sleep(1000)
		  issueSearch(urlBases.get(dataCenter).get, constructUrlParams(start.plusHours((range-1)*3), start.plusHours(3*range - 1)))
		  }
	}
}