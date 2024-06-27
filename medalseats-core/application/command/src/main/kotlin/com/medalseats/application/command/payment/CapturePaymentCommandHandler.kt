package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.PaymentStatus
import kotlinx.datetime.Clock

class CapturePaymentCommandHandler(
    private val paymentRepository: PaymentRepository
) {

    suspend fun handle(command: CapturePaymentCommand) {
        val payment = paymentRepository.findById(command.aggregateId) ?: throw Exception("as")

        paymentRepository.update(
            payment.copy(
                status = PaymentStatus.CAPTURED,
                updatedAt = Clock.System.now()
            )
        )
    }
}
