package com.medalseats.adapter.http.common

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class CorsFilter : WebFilter {

    private val allowedOrigin = "*"
    private val allowedHeaders =
        "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, mode"
    private val allowedMethods = "GET, PUT, POST, DELETE, OPTIONS"
    private val maxAge = "3600"

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return Mono.defer {
            if (CorsUtils.isCorsRequest(exchange.request)) {
                val headers = exchange.response.headers
                headers.add("Access-Control-Allow-Origin", allowedOrigin)
                headers.add("Access-Control-Allow-Methods", allowedMethods)
                headers.add("Access-Control-Max-Age", maxAge)
                headers.add("Access-Control-Allow-Headers", allowedHeaders)

                if (exchange.request.method == HttpMethod.OPTIONS) {
                    exchange.response.statusCode = HttpStatus.OK
                    return@defer exchange.response.setComplete()
                }
            }
            return@defer chain.filter(exchange)
        }
    }
}
