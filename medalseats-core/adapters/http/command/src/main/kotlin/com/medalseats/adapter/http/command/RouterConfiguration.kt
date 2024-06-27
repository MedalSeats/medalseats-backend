package com.medalseats.adapter.http.command

import com.medalseats.adapter.http.command.account.AccountHttpHandler
import com.medalseats.adapter.http.command.payment.PaymentCommandHttpHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

fun routerManagement(
    accountHttpHandler: AccountHttpHandler,
    paymentCommandHttpHandler: PaymentCommandHttpHandler
) = coRouter {
    accept(MediaType.APPLICATION_JSON).nest {
        "/account".nest {
            POST("") { req ->
                accountHttpHandler.createAccount(req)
            }
            POST("/sign-in") { req ->
                accountHttpHandler.signInAccount(req)
            }
        }

        "/payment".nest {
            POST("/authorize") { req ->
                paymentCommandHttpHandler.authorize(req)
            }

            "/{paymentId:$UUID_REGEX}".nest {
                PUT("/capture") { req ->
                    paymentCommandHttpHandler.capture(req)
                }

                PUT("/expire") { req ->
                    paymentCommandHttpHandler.expire(req)
                }

                PUT("/refund") { req ->
                    paymentCommandHttpHandler.refund(req)
                }
            }
        }
    }
}
