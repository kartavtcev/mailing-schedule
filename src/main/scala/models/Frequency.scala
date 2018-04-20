package models

import java.time.LocalDate

sealed trait Frequency {
  def filterDates (dates : List[LocalDate]) : List[LocalDate] = this match {
    case MonthDate(date) => dates.filter(_.getDayOfMonth == date)
    case WeekDays(days) =>
      dates.filter(date =>
        days.filter( (d : models.WeekDay) =>
          d.day.equalsIgnoreCase(date.getDayOfWeek.name)).length >= 1)
    case EveryDay => dates
    case Never => List.empty
    // pattern matching above is exhaustive, so no need for "case _ => ..."
  }
}

case class MonthDate(date : Int) extends Frequency

object MonthDate {
  def apply(date : Int): Option[MonthDate] = date match {
    case i if(i >= 1 && i <= 28) => Some(new MonthDate(i))  // By terms of the exercise monthly dates range is limited to 28.
    case _ => None
  }
}

case class WeekDays(days : List[models.WeekDay]) extends Frequency
object EveryDay extends Frequency
object Never extends Frequency