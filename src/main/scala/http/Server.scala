package net.benlamlih
package http

import domain.*
import domain.account.AccountService

import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.*
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.server.Router
import org.http4s.{HttpRoutes, server}
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

class Server(accountService: AccountService) {

  implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

  private val accountController: AccountController = AccountController(accountService)
  private final val routes: HttpRoutes[IO]         = accountController.routes
  // val services = accountController.routes <+>
  private final val httpApp                        = Router("/api" -> routes).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
