package com.medalseats.adapter.http.query.match.response

import com.medalseats.adapter.http.common.response.MonetaryAmountResponse
import com.medalseats.application.query.match.FindAllMatchesQueryProjection
import com.medalseats.application.query.match.FindMatchByIdQueryProjection
import com.unicamp.medalseats.toBigDecimal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
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
    val availableTickets: ImmutableList<TicketResponse>
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
                availableTickets = availableTickets.map { ticket ->
                    MatchResponse.TicketResponse(
                        category = ticket.category,
                        price = MonetaryAmountResponse(
                            value = ticket.price.toBigDecimal().toDouble(),
                            currency = ticket.price.currency.currencyCode
                        )
                    )
                }.toPersistentList()
            )
        }
    }

fun FindAllMatchesQueryProjection.toMatchResponse(): ImmutableList<MatchResponse> =
    this.matches.map {
        with(it) {
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
                availableTickets = availableTickets.map { ticket ->
                    MatchResponse.TicketResponse(
                        category = ticket.category,
                        price = MonetaryAmountResponse(
                            value = ticket.price.toBigDecimal().toDouble(),
                            currency = ticket.price.currency.currencyCode
                        )
                    )
                }.toPersistentList()
            )
        }
    }.toPersistentList()
