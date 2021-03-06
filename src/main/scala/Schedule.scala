import java.time.LocalDate
import scala.collection._

import models._

object Schedule {
  val forecastDays = 90

  // Is this FUNCTION a PURE one? https://alvinalexander.com/scala/how-to-create-scala-methods-no-side-effects-pure-functions
  // startDate is external parameter
  // concurrent mutable map is internal state, returns same sorted output for same input
  // function does not modify external state, even external Frequency.filterDates dependency is pure function
  // => Schedule.build can be considered PURE FUNCTION

  def build(clientFreqs : List[ClientWithFrequency], startDate: LocalDate) : List[ClientsPerDate] = {

    // Estimanting computational complexity, becomes clear that: Customers # >> Days #
    // Because mailing list can have thousands of clients, but it's planned for 1 year or less
    // Schedule would be updated per each client DB update.
    // So, algorithm should iterate through customers once (+ Nothing skips current customer), and all dates in parallel per client.

    val dates = (1 to forecastDays) map (startDate.plusDays(_)) toList

    // mutable map is not cool in purely functional way, but memory efficient (no dates duplicates)
    // CONCURRENT:
    // "As a general heuristic, speed-ups tend to be noticeable when the size of the collection is large, typically several thousand elements."
    // If CONCURRENT collection is not enough, use Akka actors / Futures (Future / .par can be used to build immutable pipelines).
    val datesMap : concurrent.Map[LocalDate, List[Client]] = concurrent.TrieMap(dates map ((_, Nil)) : _*)

    clientFreqs.par foreach  { cf =>     // .par is CONCURRENT
      cf.frequency.filterDates(dates) foreach { date =>
          synchronized {  // make sure Value read + write are atomic per Key // https://issues.scala-lang.org/browse/SI-7943
            val list = datesMap.getOrElse(date, Nil)
            datesMap.update(date, list ::: List(cf.client))
          }
      }
    }
    // sort dates, clients after CONCURRENT, as we must return same result (incl. order) to be Referential Transparent
    (datesMap map { case(k,v) =>
                      new ClientsPerDate(WeekDay(k.getDayOfWeek.name).get, k, v.sortWith(_.name < _.name)) } toList)
    .sortWith((_1, _2) => _1.date.isBefore(_2.date))
  }
}