package com.medalseats.adapter.http.query.match.response

import com.medalseats.application.query.match.FindMatchByIdQueryProjection
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MatchResponse(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: Long,
    val geolocation: Geolocation,
    val bannerUrl: String,
    val stadiumUrl: String
) {


    @Serializable
    data class Geolocation(
        val latitude: Long,
        val longitude: Long
    )
}

fun FindMatchByIdQueryProjection.toMatchResponse(): MatchResponse? =
    this.match?.let { match ->
        with(match) {
            MatchResponse(
                id = id.toString(),
                title = title,
                subtitle = subtitle,
                description = description,
                stadiumUrl = stadiumUrl,
                bannerUrl = bannerUrl,
                date = date.toEpochMilliseconds(),
                geolocation = MatchResponse.Geolocation(
                    latitude = geolocation.latitude,
                    longitude = geolocation.longitude
                )
            )
        }
    }
