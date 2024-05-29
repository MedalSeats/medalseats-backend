package com.medalseats.application.query.match

import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchRepository
import com.unicamp.medalseats.match.toMatchId
import kotlinx.collections.immutable.toImmutableList

class FindMatchByIdQueryHandler(
    private val matchRepository: MatchRepository
) {

    suspend fun handle(query: FindMatchByIdQuery): FindMatchByIdQueryProjection =
        FindMatchByIdQueryProjection(
            matchRepository.findById(query.matchId.toMatchId())?.toMatch()
        )

    private fun Match.toMatch() = FindMatchByIdQueryProjection.Match(
        id = id.toUUID(),
        title = title,
        subtitle = subtitle,
        description = description,
        stadium = FindMatchByIdQueryProjection.Match.Stadium(
            name = stadium.name,
            imageUrl = stadium.imageUrl
        ),
        bannerUrl = bannerUrl,
        date = date,
        geolocation = FindMatchByIdQueryProjection.Match.Geolocation(
            latitude = geolocation.latitude,
            longitude = geolocation.longitude
        ),
        iconUrl = iconUrl,
        availableTickets = availableTickets.toProjection()
    )

    private fun List<Match.Ticket>.toProjection(): List<FindMatchByIdQueryProjection.Match.Ticket> =
        this.map { it.toProjection() }.toImmutableList()

    private fun Match.Ticket.toProjection() = FindMatchByIdQueryProjection.Match.Ticket(
        category = this.category,
        price = this.price
    )
}
