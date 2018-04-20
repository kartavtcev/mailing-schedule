import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.scalatest._
import models._

class FrequencySuite extends FunSuite {
  test("WeekDays Frequency filters proper dates") {
    // ARRANGE
    val startDate = LocalDate.parse("01-April-2018", DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
    val forecastDays = 10
    val dates = (0 to forecastDays - 1) map (startDate.plusDays(_)) toList
    val tuesdayAnycase = "Tuesday"
    val tuesdayUpperCase = "TUESDAY"
    // ACT
    val frequencyWeekDays = WeekDays(List(WeekDay(tuesdayAnycase)))
    // ASSERT
    val result = frequencyWeekDays.filterDates(dates)
    assert(result.length == 2 && result.head.getDayOfWeek.name == tuesdayUpperCase)
  }

  test("MonthDate Frequency filters proper dates") {
    // ARRANGE
    val startDate = LocalDate.parse("01-April-2018", DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))
    val forecastDays = 10
    val dates = (0 to forecastDays - 1) map (startDate.plusDays(_)) toList
    val dateOfMonth = 5
    // ACT
    val frequencyMonthDate = MonthDate(dateOfMonth)
    // ASSERT
    val result = frequencyMonthDate.filterDates(dates)
    assert(result.length == 1 && result.head.getDayOfMonth == dateOfMonth)
  }
}