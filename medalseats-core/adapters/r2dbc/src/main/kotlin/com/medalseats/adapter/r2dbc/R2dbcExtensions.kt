package com.medalseats.adapter.r2dbc

import io.r2dbc.spi.Readable
import org.springframework.r2dbc.core.DatabaseClient

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> Readable.get(identifier: String): T = this.get(identifier, T::class.java)!!

inline fun <reified T> Readable.getOrNull(identifier: String) = this.get(identifier, T::class.java)

public fun String.where(sql: String?) =
    sql?.let { "${this.trimEnd()} $sql" } ?: this

fun String.orderBy(sql: String?) =
    sql?.let { "${this.trimEnd()} $sql" } ?: this

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotNull(name: String, value: T?) =
    value?.let { this.bind(name, it as Any) } ?: this
