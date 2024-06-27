package com.medalseats.application.command.payment

import com.unicamp.medalseats.match.MatchId
import com.unicamp.medalseats.payment.PaymentId
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Instant
import javax.money.MonetaryAmount

data class AuthorizePaymentCommand(
    val aggregateId: PaymentId,
    val createdOn: Instant,
    val metadata: ImmutableMap<String, String>,
    val matchId: MatchId,
    val email: String,
    val amount: MonetaryAmount
)
