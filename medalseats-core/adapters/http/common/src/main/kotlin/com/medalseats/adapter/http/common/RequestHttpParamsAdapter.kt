package com.medalseats.adapter.http.common

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

const val DEFAULT_OFFSET = 0
const val DEFAULT_LIMIT = 10

class RequestHttpParamsAdapter(private val req: ServerRequest) {

    val offset by lazy {
        req.queryParamOrNull("offset")?.toInt() ?: DEFAULT_OFFSET
    }

    val limit by lazy {
        req.queryParamOrNull("limit")?.toInt() ?: DEFAULT_LIMIT
    }
}
