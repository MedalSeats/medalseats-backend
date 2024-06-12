package com.unicamp.medalseats.payment

import java.util.UUID
import java.util.UUID.fromString

data class Brand(
    val id: BrandId,
    val name: String
)

@JvmInline
value class BrandId(private val value: UUID) {
    fun toUUID() = value
    override fun toString() = value.toString()
}

fun UUID.toBrandId() = BrandId(this)
fun String.toBrandId() = BrandId(fromString(this))
