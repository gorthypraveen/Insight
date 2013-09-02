import org.scala_tools.time.Imports._

object PlayGround { 
  
  def main(args: Array[String]): Unit = 
    {
		  println("Helloworld!")
		  testXML()
    }

  def testXML() : Unit = {
		val configFile =  scala.xml.XML.loadFile("ixoconfiguration.xml")
		//println(configFile);
		val projectName = (configFile \\ "project").map(project => (project \ "@name").text)
		val applications = (configFile \\ "application")
		val poolNames = applications.map(app => (app \ "@pool").text)
		println(poolNames)
		
		val date: DateTime = new DateTime("20013-12-13")
		println(date.dayOfMonth().text)
  }
}