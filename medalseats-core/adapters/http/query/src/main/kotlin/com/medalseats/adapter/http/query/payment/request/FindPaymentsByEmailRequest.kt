package com.medalseats.adapter.http.query.payment.request

import kotlinx.serialization.Serializable

@Serializable
data class FindPaymentsByEmailRequest(
    val email: String
)
