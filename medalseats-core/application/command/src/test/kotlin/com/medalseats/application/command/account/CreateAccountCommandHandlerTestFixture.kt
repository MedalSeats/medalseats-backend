// File: src/test/kotlin/com/medalseats/application/command/account/CreateAccountCommandHandlerTestFixture.kt

package com.medalseats.application.command.account

import com.unicamp.medalseats.account.Account
import com.unicamp.medalseats.account.AccountId
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object CreateAccountCommandHandlerTestFixture {

    const val VALID_NAME = "valid_name"
    const val VALID_EMAIL = "test@email.com"
    const val VALID_PASSWORD = "valid_password"
    val VALID_BIRTHDAY: Instant = Clock.System.now()

    fun command() = CreateAccountCommand(
        aggregateId = AccountId(),
        createdOn = Clock.System.now(),
        metadata = persistentMapOf(),
        name = VALID_NAME,
        email = VALID_EMAIL,
        birthday = VALID_BIRTHDAY,
        password = VALID_PASSWORD
    )

    fun account() = Account(
        id = AccountId(),
        name = VALID_NAME,
        email = VALID_EMAIL,
        birthday = VALID_BIRTHDAY,
        password = VALID_PASSWORD
    )
}
