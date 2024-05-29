package com.medalseats.application.query.match

import kotlinx.datetime.Instant
import java.util.UUID

data class FindMatchByIdQueryProjection(
    val match: Match?
) {

    data class Match(
        val id: UUID,
        val title: String,
        val subtitle: String,
        val description: String,
        val date: Instant,
        val geolocation: Geolocation,
        val bannerUrl: String,
        val stadiumUrl: String
    )

    data class Geolocation(
        val latitude: Long,
        val longitude: Long
    )
}
