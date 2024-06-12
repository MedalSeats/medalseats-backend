package com.unicamp.medalseats.payment

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant
import java.util.UUID

data class Invoice(
    val id: InvoiceId,
    val proposedAt: Instant,
    val expiresAt: Instant,
    val paymentPreparedAt: Instant?,
    val payables: ImmutableList<Payable>,
)

@JvmInline
value class InvoiceId(private val value: UUID) {
    override fun toString() = value.toString()
}

fun UUID.toInvoiceId() = InvoiceId(this)
fun String.toInvoiceId() = InvoiceId(UUID.fromString(this))
