package com.medalseats.adapter.r2dbc.match

import com.medalseats.adapter.r2dbc.get
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.selectMatch
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.whereId
import com.medalseats.adapter.r2dbc.where
import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchId
import com.unicamp.medalseats.match.MatchRepository
import com.unicamp.medalseats.match.toMatchId
import io.r2dbc.spi.Row
import kotlinx.datetime.toKotlinInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import java.time.Instant
import java.util.UUID
class MatchR2dbcRepository(private val db: DatabaseClient) : MatchRepository {
    override suspend fun findById(id: MatchId): Match? =
        db.sql(selectMatch().where(whereId(id)))
        .bind("id", id.toUUID())
        .map { row, _ ->
            row.toMatch()
        }.awaitSingleOrNull()

    private fun Row.toMatch() = Match(
        id = this.get<UUID>("id").toMatchId(),
        title = this.get<String>("title"),
        subtitle = this.get<String>("subtitle"),
        description = this.get<String>("description"),
        date = this.get<Instant>("date").toKotlinInstant(),
        geolocation = Match.Geolocation(
            latitude = this.get<Long>("latitude"),
            longitude = this.get<Long>("longitude"),
        ),
        bannerUrl = this.get<String>("banner_url"),
        stadiumUrl = this.get<String>("stadium_url"),
    )
}
