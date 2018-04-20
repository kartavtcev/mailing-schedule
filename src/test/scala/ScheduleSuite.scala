import java.time.LocalDate
import java.time.format.DateTimeFormatter

import models._
import org.scalatest._

class ScheduleSuite extends FunSuite {
  test("Schedule.build builds valid schedule on example input") {
    // ARRANGE
    val client1 = new Client("A")
    val cf1 = ClientWithFrequency(client1, EveryDay)

    val client2 = new Client("B")
    val cf2 = ClientWithFrequency(client2, MonthDate(10))

    val client3 = new Client("C")
    val cf3 = ClientWithFrequency(client3, WeekDays(List(WeekDay("Tuesday"), WeekDay("Friday"))))

    val startDate = LocalDate.parse("01-April-2018", DateTimeFormatter.ofPattern("dd-MMMM-yyyy")).minusDays(1)

    // ACT
    val result = Schedule.build(List(cf1, cf2, cf3), startDate)

    // ASSERT
    assert(result.head.date.compareTo(startDate.plusDays(1)) == 0
      && result.head.clients.head == client1)

    assert(result(2).clients.length == 2
      && result(2).clients.contains(client1)
      && result(2).clients.contains(client3))

    assert(result(9).clients.length == 3
      && result(9).clients.contains(client1)
      && result(9).clients.contains(client2)
      && result(9).clients.contains(client3))
  }
}