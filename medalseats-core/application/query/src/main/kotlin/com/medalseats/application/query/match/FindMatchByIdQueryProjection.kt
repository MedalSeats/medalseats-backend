package com.medalseats.application.query.match

import java.util.UUID

data class FindMatchByIdQueryProjection(
    val match: Match?
) {

    data class Match(
        val id: UUID
    )
}
