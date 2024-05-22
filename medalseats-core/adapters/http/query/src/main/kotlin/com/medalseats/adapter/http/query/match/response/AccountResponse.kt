package com.medalseats.adapter.http.query.match.response

import com.medalseats.application.query.match.FindMatchByIdQueryProjection
import kotlinx.serialization.Serializable

@Serializable
data class MatchResponse(
    val id: String,
)

fun FindMatchByIdQueryProjection.toMatchResponse(): MatchResponse? =
    this.match?.let { match ->
        with(match) {
            MatchResponse(
                id = id.toString(),
            )
        }
    }
