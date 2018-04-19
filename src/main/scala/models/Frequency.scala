package models

sealed trait Frequency
case class MonthDate(date : Int) extends Frequency
case class WeekDays(days : List[WeekDay]) extends Frequency
object EveryDay extends Frequency
object Never extends Frequency