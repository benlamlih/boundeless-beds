package net.benlamlih
package http

import domain.account.{AccountRepository, AccountService}
import http.Server

import cats.effect.{ExitCode, IO, IOApp}
import doobie.Transactor
import doobie.util.log
import doobie.util.log.LogHandler
import doobie.util.transactor.Transactor.Aux
import net.benlamlih.database.account.AccountRepositoryImpl

object Main extends IOApp {

  val xa: Aux[IO, Unit]                     = Transactor.fromDriverManager[IO](
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost:5432/boundless-beds",
    user = "user",
    password = "password",
    logHandler = Some(
      new LogHandler[IO] {
        override def run(logEvent: log.LogEvent): IO[Unit] = IO { println(logEvent.sql) }
      }
    )
  )
  def run(args: List[String]): IO[ExitCode] = {

    val accountRepository: AccountRepository = new AccountRepositoryImpl(xa)
    val accountService: AccountService       = new AccountService(accountRepository)
    val server                               = new Server(accountService)

    server.run(args)
  }
}
