package com.medalseats.adapter.http.query

import com.medalseats.adapter.http.query.match.MatchHttpHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

fun router(
    matchHttpHandler: MatchHttpHandler
) = coRouter {
    accept(MediaType.APPLICATION_JSON).nest {
        "/match".nest {
            GET(pattern = "/{matchId:$UUID_REGEX}") { req ->
                matchHttpHandler.findMatchById(req)
            }
        }
    }
}
