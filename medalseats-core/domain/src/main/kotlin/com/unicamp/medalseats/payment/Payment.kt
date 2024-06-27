package com.unicamp.medalseats.payment

import com.unicamp.medalseats.match.MatchId
import kotlinx.datetime.Instant
import javax.money.MonetaryAmount

data class Payment(
    val id: PaymentId,
    val status: PaymentStatus,
    val proposedAt: Instant,
    val updatedAt: Instant,
    val amount: MonetaryAmount,
    val matchId: MatchId,
    val email: String,
    val qrCode: String
)
