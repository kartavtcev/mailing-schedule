package models

import java.time.LocalDate

sealed trait Frequency
object Frequency {
  def filterDates (frequency: Frequency) (dates : List[LocalDate]) : List[LocalDate] = frequency match {
    case MonthDate(date) => dates.filter(_.getDayOfMonth == date)
    case WeekDays(days) =>
      dates.filter(date =>
        days.contains( (d : models.WeekDay) =>
          d.day.equalsIgnoreCase(date.getDayOfWeek.name)))
    case EveryDay => dates
    case Never => List.empty
  }
}

case class MonthDate(date : Int) extends Frequency
object MonthDate {
  def apply(date : Int): MonthDate = date match {
    case i if(i >= 1 && i <= 28) => MonthDate(i)  // By terms of the exercise monthly dates range is limited to 28.
    case _ => throw new IllegalArgumentException(s"Date must be in a range [1-28], instead of: $date")
  }
}

case class WeekDays(days : List[models.WeekDay]) extends Frequency
object EveryDay extends Frequency
object Never extends Frequency