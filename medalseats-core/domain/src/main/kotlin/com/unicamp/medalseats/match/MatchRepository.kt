package com.unicamp.medalseats.match

import kotlinx.collections.immutable.ImmutableList

interface MatchRepository {

    suspend fun findAll(
        term: String?,
        offset: Int,
        limit: Int
    ): ImmutableList<Match>
    suspend fun findById(matchId: MatchId): Match?
}
