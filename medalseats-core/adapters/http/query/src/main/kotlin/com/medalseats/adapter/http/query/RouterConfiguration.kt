package com.medalseats.adapter.http.query

import com.medalseats.adapter.http.query.match.MatchHttpHandler
import com.medalseats.adapter.http.query.payment.PaymentQueryHttpHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

fun router(
    matchHttpHandler: MatchHttpHandler,
    paymentQueryHttpHandler: PaymentQueryHttpHandler
) = coRouter {
    accept(MediaType.APPLICATION_JSON).nest {
        "/match".nest {
            GET(pattern = "") { req ->
                matchHttpHandler.findAll(req)
            }
            GET(pattern = "/{matchId:$UUID_REGEX}") { req ->
                matchHttpHandler.findMatchById(req)
            }
        }

        "/account".nest {
            GET(pattern = "/payments") { req ->
                paymentQueryHttpHandler.findPaymentsByEmail(req)
            }
        }
    }
}
