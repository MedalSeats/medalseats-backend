package com.unicamp.medalseats.payment

import kotlinx.collections.immutable.ImmutableList

interface PaymentRepository {

    suspend fun findById(paymentId: PaymentId): Payment?

    suspend fun findByEmail(email: String): ImmutableList<Payment>

    suspend fun save(payment: Payment)

    suspend fun update(payment: Payment)
}
