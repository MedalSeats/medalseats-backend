package com.medalseats.application.command.account

import com.unicamp.medalseats.account.AccountId
import kotlinx.datetime.Instant

data class CreateAccountCommand(
    val aggregateId: AccountId,
    val createdOn: Instant,
    val metadata: Map<String, String>,
    val name: String,
    val email: String,
    val birthday: Instant,
    val password: String
)
