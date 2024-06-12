package com.unicamp.medalseats.payment

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Instant
import java.util.UUID

sealed interface PaymentSource {

    val type: PaymentSourceType
    val paymentMethod: PaymentMethod
    val additionalData: ImmutableMap<String, String>

    data class PixPaymentSource(
        override val paymentMethod: PaymentMethod,
        override val additionalData: ImmutableMap<String, String>,
        val ttl: Instant?,
        val qrCode: String?
    ) : PaymentSource {
        override val type = PaymentSourceType.PIX
    }
}

enum class PaymentSourceType {
    PIX,
}

@JvmInline
value class PaymentSourceId(private val value: UUID) {
    override fun toString() = value.toString()
}

fun UUID.toPaymentSourceId() = PaymentSourceId(this)
fun String.toPaymentSourceId() = PaymentSourceId(UUID.fromString(this))
