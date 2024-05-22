package com.unicamp.medalseats.match

interface MatchRepository {

    suspend fun findById(matchId: MatchId): Match?
}
