package com.medalseats.adapter.http.command.account

import com.medalseats.adapter.http.command.account.request.CreateAccountRequest
import com.medalseats.adapter.http.command.account.request.SignInAccountRequest
import com.medalseats.adapter.http.command.account.request.toCreateAccountCommand
import com.medalseats.adapter.http.command.account.request.toSignInAccountCommand
import com.medalseats.application.command.account.CreateAccountCommand
import com.medalseats.application.command.account.CreateAccountCommandHandler
import com.medalseats.application.command.account.SignInAccountCommand
import com.medalseats.application.command.account.SignInAccountCommandHandler
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AccountHttpHandler(
    private val createAccountCommandHandler: CreateAccountCommandHandler,
    private val signInAccountCommandHandler: SignInAccountCommandHandler
) {
    suspend fun createAccount(req: ServerRequest): ServerResponse {
        createAccountCommandHandler.handle(req.toCreateAccountCommand())
        return ServerResponse.noContent().buildAndAwait()
    }

    private suspend fun ServerRequest.toCreateAccountCommand(): CreateAccountCommand =
        this.awaitBody<CreateAccountRequest>().toCreateAccountCommand()

    suspend fun signInAccount(req: ServerRequest): ServerResponse {
        val signedIn = signInAccountCommandHandler.handle(req.toSignInAccountCommand())
        return ServerResponse.noContent().buildAndAwait().takeIf { signedIn }
            ?: ServerResponse.status(HttpStatusCode.valueOf(401)).buildAndAwait()
    }

    private suspend fun ServerRequest.toSignInAccountCommand(): SignInAccountCommand =
        this.awaitBody<SignInAccountRequest>().toSignInAccountCommand()
}
