package com.unicamp.medalseats.payment

import java.util.UUID
import java.util.UUID.fromString

data class Receiver(
    val id: ReceiverId,
    val type: String,
    val name: String?,
    val softDescriptor: String?,
    val mcc: String?,
    val document: Document?,
    val phone: Phone?,
    val address: Address?,
    val bankInformation: BankInformation?
) {

    data class Document(
        val number: String?,
        val type: String?
    )

    data class Phone(
        val countryCode: String?,
        val number: String?
    )

    data class Address(
        val address: String?,
        val postalCode: String?,
        val city: String?,
        val state: String?
    )

    data class BankInformation(
        val agency: String?,
        val accountNumber: String?,
        val bank: Int?,
        val method: String?,
        val accountType: String?
    )
}

@JvmInline
value class ReceiverId(private val value: UUID) {
    override fun toString() = value.toString()
}

fun UUID.toReceiverId() = ReceiverId(this)
fun String.toReceiverId() = ReceiverId(fromString(this))
