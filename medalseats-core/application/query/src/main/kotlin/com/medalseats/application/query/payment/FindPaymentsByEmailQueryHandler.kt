package com.medalseats.application.query.payment

import com.unicamp.medalseats.payment.Payment
import com.unicamp.medalseats.payment.PaymentRepository
import kotlinx.collections.immutable.toImmutableList

class FindPaymentsByEmailQueryHandler(
    private val paymentRepository: PaymentRepository
) {

    suspend fun handle(query: FindPaymentsByEmailQuery) =
        FindPaymentsByEmailQueryProjection(
            with(query) {
                paymentRepository.findByEmail(
                    email = query.email
                )
            }.map { payment ->
                payment.toProjection()
            }.toImmutableList()
        )

    private fun Payment.toProjection() = FindPaymentsByEmailQueryProjection.Payment(
        id = id.toUUID(),
        status = status.name,
        proposedAt = proposedAt,
        updatedAt = updatedAt,
        amount = amount,
        matchId = matchId.toUUID()
    )
}
