package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.PaymentStatus
import kotlinx.datetime.Clock

class ExpirePaymentCommandHandler(
    private val paymentRepository: PaymentRepository
) {

    suspend fun handle(command: ExpirePaymentCommand) {
        val payment = paymentRepository.findById(command.aggregateId) ?: throw Exception("as")

        paymentRepository.update(
            payment.copy(
                status = PaymentStatus.EXPIRED,
                updatedAt = Clock.System.now()
            )
        )
    }
}
