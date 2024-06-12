package com.unicamp.medalseats.payment

import java.util.UUID
import javax.money.MonetaryAmount

data class Payable(
    val id: PayableId,
    val type: Type,
    val amount: MonetaryAmount,
    val receiver: Receiver
) {

    enum class Type {
        TICKET
    }
}

@JvmInline
value class PayableId(private val value: UUID) {
    override fun toString() = value.toString()
}

fun UUID.toPayableId() = PayableId(this)
fun String.toPayableId() = PayableId(UUID.fromString(this))
