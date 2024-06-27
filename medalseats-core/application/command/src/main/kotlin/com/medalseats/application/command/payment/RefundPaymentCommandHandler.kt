package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.PaymentStatus
import kotlinx.datetime.Clock

class RefundPaymentCommandHandler(
    private val paymentRepository: PaymentRepository
) {

    suspend fun handle(command: RefundPaymentCommand) {
        val payment = paymentRepository.findById(command.aggregateId) ?: throw Exception("as")

        paymentRepository.update(
            payment.copy(
                status = PaymentStatus.REFUNDED,
                updatedAt = Clock.System.now()
            )
        )
    }
}
