package net.benlamlih
package http

import domain.account.{AccountService, AccountServiceImpl}
import http.Server

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val accountService: AccountService = new AccountServiceImpl()
    val server                         = new Server(accountService)

    server.run(args)
  }
}
