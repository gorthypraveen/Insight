import org.scala_tools.time.Imports._
import scalaj.http.Http
import scala.util.matching.Regex
import scala.collection.immutable.Map
import scala.collection.immutable.HashMap
import main.java.search.Searcher
import main.java.search.Searcher
import main.java.search.Daily
import org.joda.time.{ DateTime => JodaDateTime}

object PlayGround { 
  
  def main(args: Array[String]): Unit = 
    {
//		  println("Helloworld!")
//		  testXML()
//		  println("--Testing Http----")
//		  testHttp()
		  println("--Testing Daily Search----")
		  DailySearch()
    }

  // Reference - http://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
  def testXML() : Unit = {
		val configFile =  scala.xml.XML.loadFile("ixoconfiguration.xml")
		//println(configFile);
		val projectName = (configFile \\ "project").map(project => (project \ "@name").text)
		val applications = (configFile \\ "application")
		val poolNames = applications.map(app => (app \ "@pool").text)
		println(poolNames)
		
		val date: DateTime = new DateTime("20013-12-13")
		println(date.getDayOfMonth())
  }
  
  def testHttp(): Unit = {
    val urlBase = "http://cal-vip-a.slc.paypal.com/cgi/calsearch.py"
    val urlParams: Map[String, String] = HashMap("p_regexp"->"test"
    						,"p_pools"->"inlineecspartaweb_ca"
    						,"p_email"->"inlineec%40paypal.com"
    						,"p_year_begin"->"2013"
    						,"p_month_begin"->"9"
    						,"p_day_begin"->"2"
    						,"p_hour_begin"->"15"
    						,"p_year_end"->"2013"
    						,"p_month_end"->"9"
    						,"p_day_end"->"2"
    						,"p_hour_end"->"15"
    						)
  //val url = urlBase +"?"+urlEntries.foldRight(""){(a,b)=> if(b.isEmpty()) a else a+"&"+b}
  //println("URL=")
  val response = Http(urlBase).params(urlParams).asString
  println(response)
  }
  
  def DailySearch(): Unit = 
   {
   val searcher = new Searcher("EC-12345678", "inlineecspartaweb_ca") with Daily
   val responses : List[String] = searcher.search(new DateTime)
   println(responses.length)
   responses.map {a => println(a)}
   }
}