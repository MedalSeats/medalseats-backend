package com.unicamp.medalseats.match

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant
import javax.money.MonetaryAmount

data class Match(
    val id: MatchId,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: Instant,
    val geolocation: Geolocation,
    val bannerUrl: String,
    val stadium: Stadium,
    val iconUrl: String,
    val availableTickets: ImmutableList<Ticket>
) {

    data class Stadium(
        val name: String,
        val imageUrl: String
    )

    data class Geolocation(
        val latitude: Long,
        val longitude: Long
    )

    data class Ticket(
        val category: String,
        val price: MonetaryAmount
    )
}
