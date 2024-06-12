package com.unicamp.medalseats.payment

import kotlinx.datetime.Instant
import javax.money.MonetaryAmount

data class Payment(
    val id: PaymentId,
    val payer: Payer,
    val status: PaymentStatus,
    val proposedAt: Instant,
    val updatedAt: Instant,
    val amount: MonetaryAmount,
    val invoice: Invoice
)
