package com.medalseats.application.query.match

import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchRepository
import com.unicamp.medalseats.match.toMatchId

class FindMatchByIdQueryHandler(
    private val matchRepository: MatchRepository,
) {

    suspend fun handle(query: FindMatchByIdQuery): FindMatchByIdQueryProjection =
        FindMatchByIdQueryProjection(
            matchRepository.findById(query.matchId.toMatchId())?.toMatch()
        )

    private fun Match.toMatch() = FindMatchByIdQueryProjection.Match(
        id = id.toUUID(),
    )
}
