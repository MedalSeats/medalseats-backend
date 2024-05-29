package com.medalseats.adapter.http.common

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

fun ServerRequest.queryParamOrNull(name: String): String? =
    this.queryParamOrNull(name)?.takeIf { it.isNotBlank() }

fun <T> ServerRequest.queryParamOrNull(name: String, transform: (String) -> T): T? =
    this.queryParamOrNull(name)?.takeIf { it.isNotBlank() }?.let(transform)
