package com.medalseats.adapter.r2dbc.common

import kotlinx.datetime.Instant

public fun Instant.quoteLiteral() = "'$this'"
