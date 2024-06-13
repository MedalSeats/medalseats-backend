package com.medalseats.adapter.http.command.account.request

import com.medalseats.application.command.account.SignInAccountCommand
import com.unicamp.medalseats.account.AccountId
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class SignInAccountRequest(
    val email: String,
    val password: String
)

fun SignInAccountRequest.toSignInAccountCommand() = SignInAccountCommand(
    createdOn = Clock.System.now(),
    metadata = persistentMapOf(),
    email = email,
    password = password
)
