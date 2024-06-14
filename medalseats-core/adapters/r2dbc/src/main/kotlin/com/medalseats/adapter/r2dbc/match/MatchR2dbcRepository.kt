package com.medalseats.adapter.r2dbc.match

import com.medalseats.adapter.r2dbc.bindIfNotNull
import com.medalseats.adapter.r2dbc.get
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.limit
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.offset
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.selectMatch
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.selectTickets
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.sortingBy
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.whereId
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.whereMatchId
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.whereTerm
import com.medalseats.adapter.r2dbc.orderBy
import com.medalseats.adapter.r2dbc.where
import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchId
import com.unicamp.medalseats.match.MatchRepository
import com.unicamp.medalseats.match.toMatchId
import com.unicamp.medalseats.withCurrency
import io.r2dbc.spi.Row
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.toKotlinInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.r2dbc.core.flow
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

class MatchR2dbcRepository(private val db: DatabaseClient) : MatchRepository {
    override suspend fun findAll(term: String?, offset: Int, limit: Int) =
        db.sql(
            selectMatch()
                .where(whereTerm(term))
                .orderBy(sortingBy("date"))
                .where(limit(limit))
                .where(offset(offset))
        )
            .bindIfNotNull("offset", offset)
            .bindIfNotNull("limit", limit)
            .bindIfNotNull("term", term)
            .map { row, _ ->
                row.toMatch()
            }.flow().map { match ->
                val availableTickets = findAvailableTickets(matchId = match.id)

                match.copy(
                    availableTickets = availableTickets.toImmutableList()
                )
            }.toList().toImmutableList()

    override suspend fun findById(id: MatchId): Match? {
        val match = db.sql(selectMatch().where(whereId(id)))
            .bind("id", id.toUUID())
            .map { row, _ ->
                row.toMatch()
            }.awaitSingleOrNull()

        val availableTickets = findAvailableTickets(matchId = id)

        return match?.copy(
            availableTickets = availableTickets.toImmutableList()
        )
    }

    private fun Row.toMatch() = Match(
        id = this.get<UUID>("id").toMatchId(),
        title = this.get<String>("title"),
        subtitle = this.get<String>("subtitle"),
        description = this.get<String>("description"),
        date = this.get<Instant>("date").toKotlinInstant(),
        geolocation = Match.Geolocation(
            latitude = this.get<Long>("latitude"),
            longitude = this.get<Long>("longitude")
        ),
        bannerUrl = this.get<String>("banner_url"),
        stadium = Match.Stadium(
            name = this.get<String>("stadium_name"),
            imageUrl = this.get<String>("stadium_url")
        ),
        iconUrl = this.get<String>("icon_url"),
        availableTickets = persistentListOf()
    )

    private suspend fun findAvailableTickets(
        matchId: MatchId
    ) = db.sql(
        selectTickets()
            .where(whereMatchId(matchId))
    )
        .bind("matchId", matchId.toUUID())
        .map { row, _ ->
            row.toTicket()
        }.flow().toList()

    private fun Row.toTicket() = Match.Ticket(
        category = this.get<String>("category"),
        price = this.get<BigDecimal>("amount") withCurrency this.get<String>("currency")
    )
}
