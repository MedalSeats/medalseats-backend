package com.unicamp.medalseats.account.exception

import com.unicamp.medalseats.account.AccountId

open class AccountException(message: String) : Exception(message) {

    class AccountConflictException(
        val id: AccountId
    ) : AccountException(
        "Account already exists using email $id"
    )

    class AccountNotFoundException(
        val id: AccountId
    ) : AccountException(
        "Account $id not found"
    )

    class AccountEmailNotFoundException(
        val email: String
    ) : AccountException(
        "Account $email email not found"
    )
}
