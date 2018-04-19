import models._
import java.time.LocalDate

object Schedule {
  val forecastDays = 90
  // TODO: start date = (now + 1)
  def build(clientFreqs : List[ClientWithFrequency], startDate: LocalDate) : List[ClientsPerDate] = {

    // Estimanting computational complexity, becomes clear that: Customers # >> Days #
    // Because mailing list can have Millions of clients, but it's planned for 1 year or less
    // Schedule would be updated per each clients data base update.
    // So, algorithm should iterate through customers once (+ Nothing skips current customer), and all dates (in parallel may be).

    val dates = (0 to forecastDays) map (startDate.plusDays(_)) toList

    // mutable map is not cool, but memory efficient & easy to read code
    // https://stackoverflow.com/questions/5042878/how-can-i-convert-immutable-map-to-mutable-map-in-scala
    val datesMap : collection.mutable.Map[LocalDate, List[Client]] = collection.mutable.Map(dates map ((_, List[Client]())) : _*)

    clientFreqs foreach  { cf =>
        Frequency.filterDates(cf.frequency, dates) foreach { date =>
          val list = datesMap.get(date)
          datesMap.updated(date, list :: List(cf.client))
        }
    }

    datesMap map { case(k,v) => new ClientsPerDate(WeekDay(k.getDayOfWeek.name), k, v) } toList
  }
}