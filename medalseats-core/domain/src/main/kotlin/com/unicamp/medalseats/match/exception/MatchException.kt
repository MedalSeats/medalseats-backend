package com.unicamp.medalseats.match.exception

import com.unicamp.medalseats.match.MatchId

open class MatchException(message: String) : Exception(message) {

    class MatchNotFoundException(
        val id: MatchId
    ) : MatchException(
        "Match $id not found"
    )
}
