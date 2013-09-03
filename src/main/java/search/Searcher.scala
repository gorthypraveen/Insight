package main.java.search
import org.scala_tools.time.Imports._
import scalaj.http.Http
import scala.collection.immutable.Map
import scala.collection.immutable.HashMap

class Searcher(override val item: String, override val pool:String) extends Search
