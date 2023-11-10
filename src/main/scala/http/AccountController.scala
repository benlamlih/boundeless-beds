package net.benlamlih
package http

import domain.account.*

import cats.effect.*
import eu.timepit.refined.*
import eu.timepit.refined.api.{RefType, Refined}
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Uuid
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.*
import io.circe.generic.auto.*
import io.circe.refined.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class AccountController(service: AccountService) {

  implicit val accountDecoder: EntityDecoder[IO, AccountRequest] = jsonOf[IO, AccountRequest]

  case class User(name: NonEmptyString, email: Email, phoneNumber: PhoneNumber)
  implicit val decoder: EntityDecoder[IO, User] = jsonOf[IO, User]

  implicit def logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  private def responseFromAccountOption(accountOpt: Option[Account]): IO[Response[IO]] =
    accountOpt.fold[IO[Response[IO]]](NotFound("Account not found."))(a => Ok(a.asJson))

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case req @ POST -> Root / "accounts" =>
      val io: IO[Response[IO]] =
        for {
          _       <- logger.info("Creating a new account")
          request <- req.as[AccountRequest]
          account <- service.createAccount(request.fullName, request.email, request.phoneNumber)
          _       <- logger.info(s"Account created with UUID: ${account.id}")
          res     <- Created(account.asJson)
        } yield res
      io.handleErrorWith(e => InternalServerError(e.getMessage))

    case GET -> Root / "accounts" / IdVar(uuid) =>
      val io: IO[Response[IO]] =
        for {
          _       <- logger.info(s"Fetching account with UUID: $uuid")
          account <- service.getAccount(uuid)
          res     <- responseFromAccountOption(account)
        } yield res
      io.handleErrorWith(e => InternalServerError(e.getMessage))

    case GET -> Root / "accounts" =>
      val io: IO[Response[IO]] = for {
        _        <- logger.info("Fetching all accounts")
        accounts <- service.getAllAccounts
        res      <- Ok(accounts.asJson)
      } yield res

      io.handleErrorWith(e => InternalServerError(e.getMessage))

    case req @ PUT -> Root / "accounts" / IdVar(uuid) =>
      val io: IO[Response[IO]] = for {
        _       <- logger.info(s"Updating account with UUID: $uuid")
        request <- req.decodeJson[AccountRequest]
        account <- service.updateAccount(uuid, request.fullName, request.email, request.phoneNumber)
        res     <- responseFromAccountOption(account)
      } yield res

      io.handleErrorWith(e => InternalServerError(e.getMessage))

    case DELETE -> Root / "accounts" / IdVar(uuid) =>
      val io = for {
        account <- service.deleteAccount(uuid)
        _       <- logger.info(s"Account with UUID: $uuid deleted successfully")
        res     <- NoContent()
      } yield res
      io.handleErrorWith(e => InternalServerError(e.getMessage))

  }
}

case class AccountRequest(fullName: NonEmptyString, email: Email, phoneNumber: PhoneNumber)
