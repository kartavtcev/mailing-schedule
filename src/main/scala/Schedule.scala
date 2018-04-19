import java.time.LocalDate
import scala.collection._

import models._

object Schedule {
  val forecastDays = 90
  // TODO: start date = (now + 1)
  def build(clientFreqs : List[ClientWithFrequency], startDate: LocalDate) : List[ClientsPerDate] = {

    // Estimanting computational complexity, becomes clear that: Customers # >> Days #
    // Because mailing list can have Millions of clients, but it's planned for 1 year or less
    // Schedule would be updated per each clients data base update.
    // So, algorithm should iterate through customers once (+ Nothing skips current customer), and all dates (in parallel may be).

    val dates = (0 to forecastDays) map (startDate.plusDays(_)) toList

    // mutable map is not cool, but memory efficient & easy to read code + PARALLEL (concurrent)
    // https://stackoverflow.com/questions/5042878/how-can-i-convert-immutable-map-to-mutable-map-in-scala

    // Why parallel collection choice? Any speed improvement? :
    // "As a general heuristic, speed-ups tend to be noticeable when the size of the collection is large, typically several thousand elements."
    // If parallel collection would not solve problem, use Akka actors and futures.

    val datesMap : concurrent.Map[LocalDate, List[Client]] = concurrent.TrieMap(dates map ((_, List[Client]())) : _*)

    // .par is PARALLEL (concurrent)
    clientFreqs.par foreach  { cf =>
        Frequency.filterDates(cf.frequency, dates) foreach { date =>
          synchronized {  // make sure Value read + write are atomic per Key
            val list = datesMap.get(date)
            datesMap.updated(date, list :: List(cf.client))
          }
        }
    }
    // sort clients after PARALLEL (concurrent)
    datesMap map { case(k,v) => new ClientsPerDate(WeekDay(k.getDayOfWeek.name), k, v.sortWith(_.name < _.name)) } toList
  }
}