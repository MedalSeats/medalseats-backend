package com.unicamp.medalseats.match

data class Match(
    val id: MatchId,
    val name: String,
    val geolocation: Geolocation
) {

    data class Geolocation(
        val latitude: Long,
        val longitude: Long
    )
}
