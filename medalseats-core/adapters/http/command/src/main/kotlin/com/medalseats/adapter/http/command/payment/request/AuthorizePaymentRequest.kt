package com.medalseats.adapter.http.command.payment.request

import com.medalseats.adapter.http.common.serializer.UuidSerializer
import com.medalseats.application.command.payment.AuthorizePaymentCommand
import com.unicamp.medalseats.match.MatchId
import com.unicamp.medalseats.payment.PaymentId
import com.unicamp.medalseats.withCurrency
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.UUID
import javax.money.MonetaryAmount

@Serializable
data class AuthorizePaymentRequest(
    @Serializable(UuidSerializer::class)
    val matchId: UUID,
    val email: String,
    val amount: MonetaryAmount
) {

    @Serializable
    data class MonetaryAmount(
        val value: Float,
        val currency: String
    )
}

fun AuthorizePaymentRequest.toAuthorizePaymentCommand() = AuthorizePaymentCommand(
    aggregateId = PaymentId(),
    createdOn = Clock.System.now(),
    metadata = persistentMapOf(),
    amount = amount.value withCurrency amount.currency,
    matchId = MatchId(matchId),
    email = email
)
