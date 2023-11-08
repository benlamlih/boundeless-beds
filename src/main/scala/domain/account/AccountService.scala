package net.benlamlih
package domain.account

import cats.effect.IO

trait AccountService {
  def sayHello(name: String): IO[String]
}
