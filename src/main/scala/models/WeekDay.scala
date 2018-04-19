package models

sealed abstract class WeekDay(val day: String)
object WeekDay {
  case object Mon extends WeekDay("MON")
  case object Tue extends WeekDay("TUE")
  case object Wed extends WeekDay("WED")
  case object Thu extends WeekDay("THU")
  case object Fri extends WeekDay("FRI")
  case object Sat extends WeekDay("SAT")
  case object Sun extends WeekDay("SUN")

  val weekDays: List[WeekDay] =
    List(Mon, Tue, Wed, Thu, Fri, Sat, Sun)

  def apply(s: String): WeekDay = weekDays.find(_.day.equalsIgnoreCase(s)) match {
    case Some(d) => d
    case _ => throw new IllegalArgumentException(s"Invalid value for day of week: $s")
  }
}