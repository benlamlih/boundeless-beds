package net.benlamlih
package http

import domain.account.AccountService

import cats.effect.*
import org.http4s.*
import org.http4s.dsl.io.*

class AccountController(service: AccountService) {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      service.sayHello(name).flatMap(Ok(_))

    case GET -> Root / "hey" / name =>
      service.sayHello(name + ", nice to meet you! :)").flatMap(Ok(_))
  }
}
