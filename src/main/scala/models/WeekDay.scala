package models

// TODO: can be replaced with java.time.DayOfWeek ,  but ... i did some custom logic here, so it's convenient

sealed abstract class WeekDay(val day: String) {
  override def toString: String = day.substring(0, 3)
}
object WeekDay {
  case object Mon extends WeekDay("MONDAY")
  case object Tue extends WeekDay("TUESDAY")
  case object Wed extends WeekDay("WEDNESDAY")
  case object Thu extends WeekDay("THURSDAY")
  case object Fri extends WeekDay("FRIDAY")
  case object Sat extends WeekDay("SATURDAY")
  case object Sun extends WeekDay("SUNDAY")

  val weekDays: List[WeekDay] =
    List(Mon, Tue, Wed, Thu, Fri, Sat, Sun)

  def apply(s: String): WeekDay = weekDays.find(_.day.contains(s.toUpperCase())) match {
    case Some(d) => d
    case _ => throw new IllegalArgumentException(s"Invalid value for day of week: $s")
  }
}