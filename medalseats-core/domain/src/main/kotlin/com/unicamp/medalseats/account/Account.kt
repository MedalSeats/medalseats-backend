package com.unicamp.medalseats.account

import kotlinx.datetime.Instant

data class Account(
    val id: AccountId,
    val name: String,
    val email: String,
    val password: String,
    val birthday: Instant
)
