package com.medalseats.adapter.http.common

import com.medalseats.adapter.http.common.ErrorCode.MDS_000
import com.medalseats.adapter.http.common.ErrorCode.MDS_001
import com.medalseats.adapter.http.common.ErrorCode.MDS_002
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import org.valiktor.ConstraintViolationException
import reactor.core.publisher.Mono
import java.text.MessageFormat
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

private const val MESSAGES = "messages"
private val LOGGER = KotlinLogging.logger { }

@Order(-1)
@Component
class ErrorHandler : WebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val locale = exchange.localeContext.locale ?: Locale.getDefault()
        val (statusCode, response) = ex.toResponse(locale)
        val dataBuffer = exchange.response.bufferFactory().wrap(Json.encodeToString(response).toByteArray())

        exchange.response.headers.contentType = APPLICATION_JSON
        exchange.response.statusCode = statusCode
        return exchange.response.writeWith(Mono.just(dataBuffer))
    }
}

suspend fun Throwable.toServerResponse(): ServerResponse {
    val locale = Locale.getDefault()
    val (statusCode, response) = toResponse(locale)

    return status(statusCode).bodyValueAndAwait(response)
}

private fun Throwable.toResponse(locale: Locale): Pair<HttpStatusCode, ErrorResponse> =
    when (this) {
        is ConstraintViolationException -> toResponse(
            locale,
            MDS_000,
            UNPROCESSABLE_ENTITY,
            "$constraintViolations"
        )

        is ResponseStatusException -> toResponse(locale, MDS_000, statusCode)
        is SerializationException -> toResponse(locale, MDS_001, UNPROCESSABLE_ENTITY)
        is IllegalArgumentException -> toResponse(locale, MDS_002, UNPROCESSABLE_ENTITY)
        else -> toResponse(locale, MDS_000, INTERNAL_SERVER_ERROR)
    }

private inline fun <reified T : Throwable> T.toResponse(
    locale: Locale,
    errorCode: ErrorCode,
    statusCode: HttpStatusCode,
    vararg variables: String = arrayOf()
): Pair<HttpStatusCode, ErrorResponse> {
    val i18nMessage = messageOf(
        messageKey = errorCode.messageKey,
        locale = locale,
        variables = variables
    )

    val fullMessage = """
        [${statusCode.value()}] [$errorCode] [${T::class.simpleName}] ${this.message ?: ""} | $i18nMessage"
    """

    if (statusCode.is5xxServerError) {
        LOGGER.error(this) { fullMessage }
    } else {
        LOGGER.warn { fullMessage }
    }

    return statusCode to ErrorResponse(
        message = i18nMessage,
        localizedMessage = i18nMessage
    )
}

private fun messageOf(
    messageKey: String,
    locale: Locale,
    fallback: String? = null,
    vararg variables: String = arrayOf()
): String =
    try {
        val pattern = ResourceBundle.getBundle(MESSAGES, locale).getString(messageKey)

        MessageFormat.format(pattern, *variables)
    } catch (ex: MissingResourceException) {
        LOGGER.warn { "Missing resource key ${ex.key} for locale $locale" }
        fallback ?: messageOf(
            messageKey = MDS_000.messageKey,
            locale = locale,
            fallback = "Ocorreu uma falha interna durante a requisição. Tente novamente."
        )
    }
