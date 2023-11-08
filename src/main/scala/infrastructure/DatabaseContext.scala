package net.benlamlih
package infrastructure

import io.getquill.*

object DatabaseContext {
  lazy val ctx = new H2JdbcContext(SnakeCase, "db")
}
