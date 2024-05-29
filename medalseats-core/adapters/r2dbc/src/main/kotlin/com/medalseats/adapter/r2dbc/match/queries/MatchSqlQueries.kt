package com.medalseats.adapter.r2dbc.match.queries

import com.medalseats.adapter.r2dbc.common.DefaultSqlQueries
import com.unicamp.medalseats.match.MatchId

object MatchSqlQueries : DefaultSqlQueries() {

    fun selectMatch() =
        """
            SELECT
                id,
                title,
                subtitle,
                description,
                date,
                latitude,
                longitude,
                banner_url,
                stadium_url,
                stadium_name,
                icon_url
            FROM match
            WHERE 1 = 1
        """

    fun selectTickets() =
        """
            SELECT
                category,
                amount,
                currency
            FROM ticket
            WHERE 1 = 1
        """

    fun whereId(id: MatchId?) =
        id?.let {
            """
                AND id = :id
            """
        }

    fun whereMatchId(matchId: MatchId?) =
        matchId?.let {
            """
                AND match_id = :matchId
            """
        }

}
