package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.PaymentId
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Instant

data class RefundPaymentCommand(
    val aggregateId: PaymentId,
    val createdOn: Instant,
    val metadata: ImmutableMap<String, String>
)
