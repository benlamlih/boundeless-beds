package net.benlamlih
package domain.account

import cats.effect.IO
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid
import eu.timepit.refined.types.all.NonEmptyString

import java.util.UUID
class AccountService(repository: AccountRepository) {

  override def createAccount(
      fullName: NonEmptyString,
      email: String Refined Email,
      phoneNumber: String Refined PhoneNumber
  ): IO[Account] = {
    val account = Account(Refined.unsafeApply(UUID.randomUUID().toString), fullName, email, phoneNumber)
    repository.create(account).as(account)
  }

  override def getAccount(id: String Refined Uuid): IO[Option[Account]] = {
    repository.retrieve(id.value)
  }

  override def updateAccount(
      id: String Refined Uuid,
      fullName: NonEmptyString,
      email: String Refined Email,
      phoneNumber: String Refined PhoneNumber
  ): IO[Option[Account]] = {
    val account = Account(id, fullName, email, phoneNumber)
    repository.update(account).map(_ => Some(account))
  }

  override def deleteAccount(id: String Refined Uuid): IO[Boolean] = {
    repository.delete(id.value).map(_ > 0)
  }
}
