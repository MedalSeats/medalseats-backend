package com.medalseats.application.command.account

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Instant

data class SignInAccountCommand(
    val createdOn: Instant,
    val metadata: ImmutableMap<String, String>,
    val email: String,
    val password: String
)
