package com.unicamp.medalseats.match

import java.util.UUID
import java.util.UUID.randomUUID

@JvmInline
value class MatchId(private val value: UUID = randomUUID()) {
    override fun toString() = value.toString()
    fun toUUID() = value
}

fun UUID.toMatchId() = MatchId(this)
fun String.toMatchId() = MatchId(UUID.fromString(this))
