package com.medalseats.adapter.http.query.match.response

import com.medalseats.adapter.http.common.response.MonetaryAmountResponse
import com.medalseats.application.query.match.FindMatchByIdQueryProjection
import com.unicamp.medalseats.toBigDecimal
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class MatchResponse(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: Long,
    val geolocation: GeolocationResponse,
    val bannerUrl: String,
    val stadium: StadiumResponse,
    val iconUrl: String,
    val availableTickets: List<TicketResponse>
) {

    @Serializable
    data class GeolocationResponse(
        val latitude: Long,
        val longitude: Long
    )

    @Serializable
    data class StadiumResponse(
        val name: String,
        val imageUrl: String
    )

    @Serializable
    data class TicketResponse(
        val category: String,
        val price: MonetaryAmountResponse
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
                stadium = MatchResponse.StadiumResponse(
                    name = stadium.name,
                    imageUrl = stadium.imageUrl
                ),
                bannerUrl = bannerUrl,
                date = date.toEpochMilliseconds(),
                geolocation = MatchResponse.GeolocationResponse(
                    latitude = geolocation.latitude,
                    longitude = geolocation.longitude
                ),
                iconUrl = iconUrl,
                availableTickets = availableTickets.toResponse()
            )
        }
    }

fun List<FindMatchByIdQueryProjection.Match.Ticket>.toResponse() =
    this.map { it.toResponse() }.toImmutableList()

fun FindMatchByIdQueryProjection.Match.Ticket.toResponse() =
    MatchResponse.TicketResponse(
        category = this.category,
        price = MonetaryAmountResponse(
            value = this.price.toBigDecimal().toDouble(),
            currency = this.price.currency.currencyCode
        )
    )
