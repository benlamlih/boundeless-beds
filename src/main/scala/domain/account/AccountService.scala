package net.benlamlih
package domain.account

import cats.effect.IO
import eu.timepit.refined.api.Refined
import eu.timepit.refined.types.all.NonEmptyString

import java.util.UUID as JavaUUID

class AccountService(repository: AccountRepository) {

  def createAccount(
      fullName: NonEmptyString,
      email: Email,
      phoneNumber: PhoneNumber
  ): IO[Account] = {
    val account = Account(Refined.unsafeApply(JavaUUID.randomUUID().toString), fullName, email, phoneNumber)
    repository.create(account).as(account)
  }

  def getAccount(id: UUID): IO[Option[Account]] = {
    println("before retrieving account")
    repository.retrieve(id)
  }

  def getAllAccounts: IO[List[Account]] = {
    println("here1")
    repository.retrieveAll();
  }

  def updateAccount(
      id: UUID,
      fullName: NonEmptyString,
      email: Email,
      phoneNumber: PhoneNumber
  ): IO[Option[Account]] = {
    val account = Account(id, fullName, email, phoneNumber)
    repository.update(account).map(_ => Some(account))
  }

  def deleteAccount(id: UUID): IO[Unit] = {
    repository.delete(id).map(_ => ())
  }
}
