package com.medalseats.application.command.payment

import com.unicamp.medalseats.account.AccountRepository
import com.unicamp.medalseats.account.exception.AccountException
import com.unicamp.medalseats.payment.Payment
import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.PaymentStatus
import kotlinx.datetime.Clock

class AuthorizePaymentCommandHandler(
    private val paymentRepository: PaymentRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun handle(command: AuthorizePaymentCommand): Payment {
        val account = accountRepository.findByEmail(command.email)
            ?: throw AccountException.AccountEmailNotFoundException(command.email)

        val payment = Payment(
            id = command.aggregateId,
            amount = command.amount,
            matchId = command.matchId,
            proposedAt = Clock.System.now(),
            updatedAt = Clock.System.now(),
            qrCode = command.aggregateId.toString(),
            status = PaymentStatus.PROPOSED,
            email = account.email
        )

        paymentRepository.save(payment)

        return payment
    }
}
