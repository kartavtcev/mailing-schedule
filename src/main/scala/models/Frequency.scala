package models

sealed trait Frequency

case class MonthDate(date : Int) extends Frequency
object MonthDate {
  def apply(date : Int): MonthDate = date match {
    case i if(i >= 1 && i <= 28) => MonthDate(i)  // By terms of the exercise monthly dates range is limited to 28.
    case _ => throw new IllegalArgumentException(s"Date must be in a range [1-28], instead of: $date")
  }
}

case class WeekDays(days : List[WeekDay]) extends Frequency
object EveryDay extends Frequency
object Never extends Frequency