package com.unicamp.medalseats.match

import kotlinx.datetime.Instant

data class Match(
    val id: MatchId,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: Instant,
    val geolocation: Geolocation,
    val bannerUrl: String,
    val stadiumUrl: String
) {

    data class Geolocation(
        val latitude: Long,
        val longitude: Long
    )
}
