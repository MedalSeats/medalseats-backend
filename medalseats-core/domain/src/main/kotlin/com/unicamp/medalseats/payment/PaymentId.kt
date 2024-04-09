package com.unicamp.medalseats.payment

import java.util.UUID
import java.util.UUID.randomUUID

@JvmInline
value class PaymentId(private val value: UUID = randomUUID()) {
    override fun toString() = value.toString()
    fun toUUID() = value
}

fun UUID.toPaymentId() = PaymentId(this)
fun String.toPaymentId() = PaymentId(UUID.fromString(this))
