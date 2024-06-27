package com.medalseats.adapter.http.command.payment.response

import com.medalseats.adapter.http.common.serializer.UuidSerializer
import com.unicamp.medalseats.payment.Payment
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PaymentResponse(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val qrCode: String
)

fun Payment.toResponse() = PaymentResponse(
    id = id.toUUID(),
    qrCode = qrCode
)
