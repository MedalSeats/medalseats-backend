package com.medalseats.application.query.match

import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class FindAllMatchesQueryHandler(
    private val matchRepository: MatchRepository
) {

    suspend fun handle(query: FindAllMatchesQuery) =
        FindAllMatchesQueryProjection(
            with(query) {
                matchRepository.findAll(
                    offset = offset,
                    limit = limit
                )
            }.map { match ->
                match.toProjection()
            }.toImmutableList()
        )

    private fun Match.toProjection() = FindAllMatchesQueryProjection.Match(
        id = id.toUUID(),
        title = title,
        subtitle = subtitle,
        description = description,
        stadium = FindAllMatchesQueryProjection.Match.Stadium(
            name = stadium.name,
            imageUrl = stadium.imageUrl
        ),
        bannerUrl = bannerUrl,
        date = date,
        geolocation = FindAllMatchesQueryProjection.Match.Geolocation(
            latitude = geolocation.latitude,
            longitude = geolocation.longitude
        ),
        iconUrl = iconUrl,
        availableTickets = availableTickets.toProjection()
    )

    private fun ImmutableList<Match.Ticket>.toProjection(): ImmutableList<FindAllMatchesQueryProjection.Match.Ticket> =
        this.map { it.toProjection() }.toImmutableList()

    private fun Match.Ticket.toProjection() = FindAllMatchesQueryProjection.Match.Ticket(
        category = this.category,
        price = this.price
    )
}
