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

    val dates = (0 to forecastDays) map (startDate.plusDays(_))
    //val clientFreqs foreach {}
    List.empty
  }
}