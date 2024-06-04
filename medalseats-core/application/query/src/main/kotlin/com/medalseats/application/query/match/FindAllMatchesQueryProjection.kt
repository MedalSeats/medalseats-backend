package com.medalseats.application.query.match

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant
import java.util.UUID
import javax.money.MonetaryAmount

data class FindAllMatchesQueryProjection(
    val matches: ImmutableList<Match>
) {

    data class Match(
        val id: UUID,
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
}
