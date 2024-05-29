package com.medalseats.adapter.http.command.account.request

import com.medalseats.application.command.account.CreateAccountCommand
import com.unicamp.medalseats.account.AccountId
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val name: String,
    val email: String,
    val password: String,
    val birthday: Long
)

fun CreateAccountRequest.toCreateAccountCommand() = CreateAccountCommand(
    aggregateId = AccountId(),
    createdOn = Clock.System.now(),
    metadata = emptyMap(),
    email = email,
    name = name,
    birthday = Instant.fromEpochMilliseconds(birthday),
    password = password
)
