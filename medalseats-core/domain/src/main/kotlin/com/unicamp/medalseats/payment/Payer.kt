package com.unicamp.medalseats.payment

import java.util.UUID

data class Payer(
    val id: PayerId,
    val type: Type,
    val name: String,
    val document: Document?
) {
    data class Document(
        val number: String,
        val type: String
    )

    enum class Type {
        CUSTOMER
    }
}

@JvmInline
value class PayerId(private val value: UUID) {
    override fun toString() = value.toString()
    fun toUUID() = value
}

fun UUID.toPayerId() = PayerId(this)
fun String.toPayerId() = PayerId(UUID.fromString(this))
