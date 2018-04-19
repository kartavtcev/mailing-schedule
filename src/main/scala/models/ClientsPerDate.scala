package models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ClientsPerDate(val weekDay: WeekDay, val date: LocalDate, val clients : List[Client]){
  override def toString: String = {
    // thread-safe DateTime formatter: https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
    s"$weekDay ${date.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))} ${clients.mkString(", ")}" }
}