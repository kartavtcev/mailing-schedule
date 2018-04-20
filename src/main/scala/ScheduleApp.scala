import java.time.LocalDate
import scala.io.StdIn

import annotation.tailrec

import models._
import parsers._

object ScheduleApp extends App {
  def read: List[String]= {

    @tailrec
    def reread(xs: List[String]): List[String] = {
      val s = StdIn.readLine()
      println(s)
      if (s.isEmpty() ) xs else reread(s +: xs)
    }

    reread(Nil)
  }

  def parse(ss: List[String]): List[ClientWithFrequency] = {

    @tailrec
    def reparse(xs: List[String], acc: List[Option[ClientWithFrequency]]): List[Option[ClientWithFrequency]] = {
      xs match {
        case h::t => reparse(t, Parser.tryParseInputLine(h) :: acc)
        case Nil => acc
      }
    }

    reparse(ss, Nil) flatten
  }

  println("Input format: [CustomerName] [FrequencyType] [Frequency Parameters if any]")
  println("Use space as separator of parameters. [] are here to highlight formats, don't input these brackets.")
  println("[FrequencyType] options: MonthDate, WeekDays, EveryDay, Never.")
  println("MonthDate possible parameter: [1-28] integer.")
  println("WeekDays possible parameters list: [MON MONDAY SUN SUNDAY]")
  println("Press ENTER on empty string to print schedule & exit.")
  println("Any incorrect input is lost, silently. No check for customers duplicate names. Customer name must be single word.")
  println("Example: CustomerDanielFrank WeekDays MONDAY FRIDAY")

  val parsedInput : List[ClientWithFrequency] = parse(read)

  Schedule.build(parsedInput, LocalDate.now) foreach { println(_) }
}