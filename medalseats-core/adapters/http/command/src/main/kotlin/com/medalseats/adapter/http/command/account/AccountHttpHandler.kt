package com.medalseats.adapter.http.command.account

import com.medalseats.adapter.http.command.account.request.CreateAccountRequest
import com.medalseats.adapter.http.command.account.request.toCreateAccountCommand
import com.medalseats.application.command.account.CreateAccountCommand
import com.medalseats.application.command.account.CreateAccountCommandHandler
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AccountHttpHandler(
    private val createAccountCommandHandler: CreateAccountCommandHandler
) {
    suspend fun createAccount(req: ServerRequest): ServerResponse {
        createAccountCommandHandler.handle(req.toCreateAccountCommand())
        return ServerResponse.noContent().buildAndAwait()
    }

    private suspend fun ServerRequest.toCreateAccountCommand(): CreateAccountCommand =
        this.awaitBody<CreateAccountRequest>().toCreateAccountCommand()
}
