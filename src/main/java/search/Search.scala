package main.java.search
import org.scala_tools.time.Imports._
import scalaj.http.Http
import scala.collection.mutable.HashMap
import org.joda.time.{ DateTime => JodaDateTime}

trait Search
{
	val item : String
	val pool : String
	def availableDCs: List[String] = List("SLCA", "SLCB")
	def urlBases : HashMap[String, String] = HashMap(availableDCs(0)->"http://cal-vip-a.slc.paypal.com/cgi/calsearch.py"
														,availableDCs(1)->"http://cal-vip-b.slc.paypal.com/cgi/calsearch.py")
    
	def issueSearch(urlBase : String, params : List[(String, String)]) : String =
    {
	  def internalIssueSearch(counter : Int): String =
	  {
//	    val paramList = params.toList.map(x => x._1+"="+x._2)
//	    println("URL: " + urlBase +  " Params: " + paramList.foldRight("")((a,b) =>if(b.isEmpty()) a else a+"&"+b))
	    counter match
	    {
	      case 5  => ""
	      case _ => {
	        try{
	          Http(urlBase).params(params).asString
	          val paramList = params.toList.map(x => x._1+"="+x._2)
	          val paramString = paramList.foldRight("")((a,b) =>if(b.isEmpty()) a else a+"&"+b)
	          urlBase + "?" + paramString
	        }
	        catch{
	          case e: Exception => {println("Exception block") 
	            internalIssueSearch(counter+1)}
			}
	      }
	    }
	  }
	  internalIssueSearch(0)
    }

	def constructUrlParams(dateStart : JodaDateTime, dateEnd: JodaDateTime) : List[(String, String)] =
	{
			List(("p_regexp",item)
    						,("p_pools", pool)
    						,("p_email","inlineec%40paypal.com")
    						,("p_year_begin", dateStart.year().get().toString)
    						,("p_month_begin",dateStart.monthOfYear().get().toString)
    						,("p_day_begin",dateStart.dayOfMonth().get().toString)
    						,("p_hour_begin",dateStart.hourOfDay().get().toString)
    						,("p_year_end",dateEnd.year().get().toString)
    						,("p_month_end",dateEnd.monthOfYear().get().toString)
    						,("p_day_end",dateEnd.dayOfMonth().get().toString)
    						,("p_hour_end",dateEnd.hourOfDay().get().toString)
    						)
	}
	
	def extractResultLoc(searchResponse : String) : String =
	{
	  val anchorLines : List[String] = searchResponse.lines.filter(_.contains("<a href=")).toList
	  anchorLines match
	  {
	      case u if(u.isEmpty) => "No Anchor tag"
	      case _ => { val prunedAnchorTag =  anchorLines(0)
	        								.stripSuffix("</p>")
	        								.replaceAll("href=", "href=\"")
	        								.replaceAll(".html.gz>",".html.gz\">")
	        println(prunedAnchorTag)
	        getLinkFromAnchorTag(prunedAnchorTag)
	        }
	  }
	}
	
	def getLinkFromAnchorTag(anchorTag : String) : String =
	 {
	  anchorTag match 
	   {
	     case anchor  if(!anchor.isEmpty()) => {
	    	 									val xml = scala.xml.XML.loadString("<root>"+anchorTag+"</root>")
	    	 									val aTag = (xml \\ "a")
	    	 									(aTag \ "@href").text
	     									}
	     case _ => ""
	   }
	 }

}
