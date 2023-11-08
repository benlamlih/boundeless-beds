package net.benlamlih
package domain.account

import cats.effect.IO

class AccountServiceImpl extends AccountService {
  def sayHello(name: String): IO[String] = IO {
    s"Hello, $name"
  }
}
