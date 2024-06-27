package com.medalseats.adapter.http.command.payment

import com.medalseats.adapter.http.command.payment.request.AuthorizePaymentRequest
import com.medalseats.adapter.http.command.payment.request.toAuthorizePaymentCommand
import com.medalseats.adapter.http.command.payment.response.toResponse
import com.medalseats.application.command.payment.AuthorizePaymentCommand
import com.medalseats.application.command.payment.AuthorizePaymentCommandHandler
import com.medalseats.application.command.payment.CapturePaymentCommand
import com.medalseats.application.command.payment.CapturePaymentCommandHandler
import com.medalseats.application.command.payment.ExpirePaymentCommand
import com.medalseats.application.command.payment.ExpirePaymentCommandHandler
import com.medalseats.application.command.payment.RefundPaymentCommand
import com.medalseats.application.command.payment.RefundPaymentCommandHandler
import com.unicamp.medalseats.payment.toPaymentId
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

class PaymentCommandHttpHandler(
    private val authorizePaymentCommandHandler: AuthorizePaymentCommandHandler,
    private val capturePaymentCommandHandler: CapturePaymentCommandHandler,
    private val refundPaymentCommandHandler: RefundPaymentCommandHandler,
    private val expirePaymentCommandHandler: ExpirePaymentCommandHandler
) {
    suspend fun authorize(req: ServerRequest) =
        ServerResponse.ok().bodyValueAndAwait(
            authorizePaymentCommandHandler.handle(req.toAuthorizePaymentCommand()).toResponse()
        )

    suspend fun capture(req: ServerRequest): ServerResponse {
        capturePaymentCommandHandler.handle(req.toCapturePaymentCommand())
        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun expire(req: ServerRequest): ServerResponse {
        expirePaymentCommandHandler.handle(req.toExpirePaymentCommand())
        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun refund(req: ServerRequest): ServerResponse {
        refundPaymentCommandHandler.handle(req.toRefundPaymentCommand())
        return ServerResponse.noContent().buildAndAwait()
    }

    private suspend fun ServerRequest.toAuthorizePaymentCommand(): AuthorizePaymentCommand =
        this.awaitBody<AuthorizePaymentRequest>().toAuthorizePaymentCommand()

    private fun ServerRequest.toCapturePaymentCommand() = CapturePaymentCommand(
        aggregateId = this.pathVariable("paymentId").toPaymentId(),
        createdOn = Clock.System.now(),
        metadata = persistentMapOf()
    )

    private fun ServerRequest.toRefundPaymentCommand() = RefundPaymentCommand(
        aggregateId = this.pathVariable("paymentId").toPaymentId(),
        createdOn = Clock.System.now(),
        metadata = persistentMapOf()
    )

    private fun ServerRequest.toExpirePaymentCommand() = ExpirePaymentCommand(
        aggregateId = this.pathVariable("paymentId").toPaymentId(),
        createdOn = Clock.System.now(),
        metadata = persistentMapOf()
    )
}
