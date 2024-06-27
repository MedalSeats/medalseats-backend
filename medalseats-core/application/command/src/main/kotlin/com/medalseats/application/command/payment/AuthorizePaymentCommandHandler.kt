package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.Payment
import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.PaymentStatus
import kotlinx.datetime.Clock

class AuthorizePaymentCommandHandler(
    private val paymentRepository: PaymentRepository
) {

    suspend fun handle(command: AuthorizePaymentCommand): Payment {
        val payment = Payment(
            id = command.aggregateId,
            amount = command.amount,
            matchId = command.matchId,
            proposedAt = Clock.System.now(),
            updatedAt = Clock.System.now(),
            qrCode = command.aggregateId.toString(),
            status = PaymentStatus.PROPOSED,
            email = command.email
        )

        paymentRepository.save(payment)

        return payment
    }
}
