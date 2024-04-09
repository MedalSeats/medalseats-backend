package com.unicamp.medalseats.account

import java.util.UUID
import java.util.UUID.randomUUID

@JvmInline
value class AccountId(private val value: UUID = randomUUID()) {
    override fun toString() = value.toString()
    fun toUUID() = value
}

fun UUID.toAccountId() = AccountId(this)
fun String.toAccountId() = AccountId(UUID.fromString(this))
