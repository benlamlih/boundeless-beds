package net.benlamlih
package domain.account

import cats.effect.IO
import doobie.implicits.*
import doobie.refined.implicits.*
import doobie.util.transactor.Transactor
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid

class AccountRepositoryImpl(xa: Transactor[IO]) extends AccountRepository {

  override def create(account: Account): IO[Unit] =
    sql"insert into account (id, fullName, email, phoneNumber) values (${account.id}, ${account.fullName}, ${account.email}, ${account.phoneNumber})".update.run
      .transact(xa)
      .map(_ => ())

  override def find(name: String): IO[Option[Account]] =
    sql"select * from account where fullName = $name".query[Account].option.transact(xa)

  override def retrieve(id: UUID): IO[Option[Account]] = {
    println("before executing request")
    sql"select * from account where id = ${id}".query[Account].option.transact(xa)
  }

  override def retrieveAll(): IO[List[Account]] = {
    sql"SELECT * FROM accounts"
      .query[Account]
      .to[List]
      .transact(xa)
  }

  override def update(account: Account): IO[Unit] =
    sql"update account set fullName = ${account.fullName}, email = ${account.email}, phoneNumber = ${account.phoneNumber} where id = ${account.id}".update.run
      .transact(xa)
      .map(_ => ())

  override def delete(id: UUID): IO[Unit] =
    sql"delete from account where id = ${id}".update.run
      .transact(xa)
      .map(_ => ())

}
