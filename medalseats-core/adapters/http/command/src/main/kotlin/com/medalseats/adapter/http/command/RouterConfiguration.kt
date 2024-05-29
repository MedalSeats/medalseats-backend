package com.medalseats.adapter.http.command

import com.medalseats.adapter.http.command.account.AccountHttpHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

fun routerManagement(
    accountHttpHandler: AccountHttpHandler
) = coRouter {
    accept(MediaType.APPLICATION_JSON).nest {
        "/account".nest {
            POST("") { req ->
                accountHttpHandler.createAccount(req)
            }
        }
    }
}
