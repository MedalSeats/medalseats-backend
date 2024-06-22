package com.medalseats.application.command.account

import com.unicamp.medalseats.account.Account
import com.unicamp.medalseats.account.AccountId
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock

object SignInAccountCommandHandlerTestFixture {

    const val VALID_NAME = "valid_name"
    const val VALID_EMAIL = "test@email.com"
    const val VALID_PASSWORD = "valid_password"

    fun command() = SignInAccountCommand(
        createdOn = Clock.System.now(),
        metadata = persistentMapOf(),
        email = VALID_EMAIL,
        password = VALID_PASSWORD
    )

    fun account() = Account(
        id = AccountId(),
        birthday = Clock.System.now(),
        email = VALID_EMAIL,
        password = VALID_PASSWORD,
        name = VALID_NAME
    )
}
