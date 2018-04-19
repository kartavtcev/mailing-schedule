import java.time.LocalDate

import models._

object ScheduleApp extends App {
  //println("Hello World!")

  val client1 = new Client("A")
  val cf1 = ClientWithFrequency(client1, EveryDay)

  val client2 = new Client("B")
  val cf2 = ClientWithFrequency(client2, MonthDate(10))

  val client3 = new Client("C")
  val cf3 = ClientWithFrequency(client3, WeekDays(List(WeekDay("Tuesday"), WeekDay("Friday"))))

  Schedule.build(List(cf1, cf2, cf3), LocalDate.now) foreach { println(_) }
}