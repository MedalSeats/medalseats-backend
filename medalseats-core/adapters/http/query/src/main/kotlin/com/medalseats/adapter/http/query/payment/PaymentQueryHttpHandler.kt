package com.medalseats.adapter.http.query.payment

import com.medalseats.adapter.http.query.payment.request.FindPaymentsByEmailRequest
import com.medalseats.adapter.http.query.payment.response.toResponse
import com.medalseats.application.query.payment.FindPaymentsByEmailQuery
import com.medalseats.application.query.payment.FindPaymentsByEmailQueryHandler
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class PaymentQueryHttpHandler(
    private val findPaymentsByEmailQueryHandler: FindPaymentsByEmailQueryHandler
) {
    suspend fun findPaymentsByEmail(req: ServerRequest): ServerResponse {
        val query = FindPaymentsByEmailQuery(
            email = req.pathVariable("email")
        )

        return ServerResponse.ok().bodyValueAndAwait(findPaymentsByEmailQueryHandler.handle(query).toResponse())
    }
}
