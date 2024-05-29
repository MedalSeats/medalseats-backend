package com.medalseats.adapter.http.common.response

import kotlinx.serialization.Serializable

@Serializable
data class MonetaryAmountResponse(
    val value: Double,
    val currency: String
)
