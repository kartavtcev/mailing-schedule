package parsers

import scala.util.Try
import models._

object Parser {
  def tryToInt(s: String) = Try(s.toInt).toOption

  def tryParseInputLine(input: String) : Option[ClientWithFrequency] = {
    val sp = input.split("\\s+")
    if(sp.length < 2) return None // return is required here, exception is thrown below otherwise

    val client = new Client(sp(0))
    val frequency : Option[Frequency] = sp(1).toUpperCase match {
      case "MONTHDATE" =>
        if(sp.length == 3) tryToInt(sp(2)) map (MonthDate(_)) flatten
        else None
      case "WEEKDAYS" =>
        if(sp.length >= 3) {
          val wds = sp.drop(2).map(WeekDay(_)).flatten.toList
          if(wds.isEmpty) None
          else Some(WeekDays(wds))
        } else None
      case "EVERYDAY" => Some(EveryDay)
      case "NEVER" => Some(Never)
      case _ => None
    }
    frequency map(ClientWithFrequency(client, _))
  }
}
