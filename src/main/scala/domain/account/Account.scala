package net.benlamlih
package domain.account

import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.*
import eu.timepit.refined.string.*

import java.util.UUID

val emailRegex: String       = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"
val uuidRegex: String        = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
val phoneNumberRegex: String = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$"

type Email       = MatchesRegex[`emailRegex`.type]
type PhoneNumber = MatchesRegex[`phoneNumberRegex`.type]
type ValidUUID   = MatchesRegex[`uuidRegex`.type]

/** Account Entity representing a client's profile.
  */
case class Account(
    id: UUID Refined ValidUUID,
    fullName: String Refined NonEmpty,
    email: String Refined Email,
    phoneNumber: String Refined PhoneNumber
)

object Account {

  /** Factory method to create a new account.
    */
  def create(
      fullName: String Refined NonEmpty,
      email: String Refined Email,
      phoneNumber: String Refined PhoneNumber
  ): Account =
    Account(
      Refined.unsafeApply(UUID.randomUUID()),
      fullName,
      email,
      phoneNumber
    )

}
