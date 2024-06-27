package com.medalseats.application.query.payment

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant
import java.util.UUID
import javax.money.MonetaryAmount

data class FindPaymentsByEmailQueryProjection(
    val payments: ImmutableList<Payment>
) {

    data class Payment(
        val id: UUID,
        val status: String,
        val proposedAt: Instant,
        val updatedAt: Instant,
        val amount: MonetaryAmount,
        val matchId: UUID
    )
}
