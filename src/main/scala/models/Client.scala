package models

class Client(val name : String) {
  override def toString: String = name
}
case class ClientWithFrequency(client : Client, frequency: Frequency)