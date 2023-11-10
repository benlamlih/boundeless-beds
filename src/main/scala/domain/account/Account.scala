package net.benlamlih
package domain.account

import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.*
import eu.timepit.refined.types.string.NonEmptyString

import java.util.UUID 

val emailRegex: String       = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"
val phoneNumberRegex: String = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$"

type EmailConstraint       = MatchesRegex[`emailRegex`.type]
type PhoneNumberConstraint = MatchesRegex[`phoneNumberRegex`.type]

type UUID = String Refined Uuid
type Email       = String Refined EmailConstraint
type PhoneNumber = String Refined PhoneNumberConstraint

/** Account Entity representing a client's profile.
  */
case class Account(
                    id: UUID,
                    fullName: NonEmptyString,
                    email: Email,
                    phoneNumber: PhoneNumber
)

object Account {

  /** Factory method to create a new account.
    */
  def create(
      fullName: NonEmptyString,
      email: Email,
      phoneNumber: PhoneNumber
  ): Account =
    Account(
      Refined.unsafeApply(UUID.randomUUID().toString),
      fullName,
      email,
      phoneNumber
    )

}
