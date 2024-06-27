package com.medalseats.adapter.http.command

import com.medalseats.adapter.http.command.account.AccountHttpHandler
import com.medalseats.adapter.http.command.payment.PaymentHttpHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

fun routerManagement(
    accountHttpHandler: AccountHttpHandler,
    paymentHttpHandler: PaymentHttpHandler
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
                paymentHttpHandler.authorize(req)
            }

            PUT("/capture") { req ->
                paymentHttpHandler.capture(req)
            }
        }
    }
}
