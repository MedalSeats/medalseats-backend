package com.medalseats.adapter.http.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val localizedMessage: String
)

enum class ErrorCode(val messageKey: String) {
    MDS_000(messageKey = "errors.medalseats.000"),
    MDS_001(messageKey = "errors.medalseats.001"),
    MDS_002(messageKey = "errors.medalseats.002")
}
