package com.unicamp.medalseats.payment

import java.util.UUID
import java.util.UUID.fromString

data class PaymentMethod(
    val id: PaymentMethodId,
    val name: String,
    val type: PaymentMethodType,
    val method: Method,
    val liability: String,
    val brand: Brand,
)

enum class PaymentMethodType {
    ONLINE,
    OFFLINE
}

enum class Method(val immediateCapture: Boolean) {
    PIX(immediateCapture = true),
}

@JvmInline
value class PaymentMethodId(private val value: UUID) {
    fun toUUID() = value
    override fun toString() = value.toString()
}

fun UUID.toPaymentMethodId() = PaymentMethodId(this)
fun String.toPaymentMethodId() = PaymentMethodId(fromString(this))
