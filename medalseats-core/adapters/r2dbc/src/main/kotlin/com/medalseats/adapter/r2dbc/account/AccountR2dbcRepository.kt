package com.medalseats.adapter.r2dbc.account

import com.medalseats.adapter.r2dbc.account.queries.AccountSqlQueries.insertAccount
import com.medalseats.adapter.r2dbc.account.queries.AccountSqlQueries.selectAccount
import com.medalseats.adapter.r2dbc.account.queries.AccountSqlQueries.whereEmail
import com.medalseats.adapter.r2dbc.get
import com.medalseats.adapter.r2dbc.where
import com.unicamp.medalseats.account.Account
import com.unicamp.medalseats.account.AccountRepository
import com.unicamp.medalseats.account.toAccountId
import io.r2dbc.spi.Row
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitSingleOrNull
import java.util.UUID

class AccountR2dbcRepository(private val db: DatabaseClient) : AccountRepository {
    override suspend fun findByEmail(email: String) =
        db.sql(selectAccount().where(whereEmail(email)))
            .bind("email", email)
            .map { row, _ ->
                row.toAccount()
            }.awaitSingleOrNull()


    override suspend fun register(account: Account) =
        db.sql(insertAccount())
            .bind("id", account.id.toUUID())
            .bind("name", account.name)
            .bind("email", account.email)
            .bind("password", account.password)
            .bind("birthday", account.birthday.toJavaInstant())
            .await()

    private fun Row.toAccount() = Account(
        id = this.get<UUID>("id").toAccountId(),
        name = this.get<String>("name"),
        email = this.get<String>("email"),
        birthday = this.get<Instant>("birthday"),
        password = this.get<String>("password"),
    )
}
