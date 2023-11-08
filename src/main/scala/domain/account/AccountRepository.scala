package net.benlamlih
package domain.account

import infrastructure.DatabaseContext.ctx

import cats.effect.IO
import eu.timepit.refined.auto.*
import io.getquill.*

import scala.concurrent.Future

class AccountRepository {
  def getAccountNameByName(name: String) = {
    val query1 = quote {
      query[Account].filter(account => lazyLift(account.fullName.value) == "Simo").map(lazyLift(_.fullName.value))
    }
    //    ctx.run(query1)
  }
}
