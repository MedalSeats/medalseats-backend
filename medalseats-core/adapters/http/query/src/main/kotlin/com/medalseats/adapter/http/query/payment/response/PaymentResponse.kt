package com.medalseats.adapter.http.query.payment.response

import com.medalseats.adapter.http.common.serializer.BigDecimalSerializer
import com.medalseats.adapter.http.common.serializer.UuidSerializer
import com.medalseats.application.query.payment.FindPaymentsByEmailQueryProjection
import com.unicamp.medalseats.toBigDecimal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

@Serializable
data class PaymentResponse(
    @Serializable(UuidSerializer::class)
    val id: UUID,
    val status: String,
    val proposedAt: Long,
    val updatedAt: Long,
    val amount: MonetaryAmount,
    @Serializable(UuidSerializer::class)
    val matchId: UUID
) {

    @Serializable
    data class MonetaryAmount(
        @Serializable(BigDecimalSerializer::class)
        val amount: BigDecimal,
        val currency: String
    )
}

fun FindPaymentsByEmailQueryProjection.toResponse(): ImmutableList<PaymentResponse> =
    this.payments.map {
        with(it) {
            PaymentResponse(
                id = id,
                status = status,
                proposedAt = proposedAt.toEpochMilliseconds(),
                updatedAt = updatedAt.toEpochMilliseconds(),
                amount = PaymentResponse.MonetaryAmount(
                    amount = amount.toBigDecimal(),
                    currency = amount.currency.currencyCode
                ),
                matchId = matchId
            )
        }
    }.toPersistentList()
