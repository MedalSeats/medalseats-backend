package com.medalseats.application.command.payment

import com.unicamp.medalseats.payment.BrandId
import com.unicamp.medalseats.payment.InvoiceId
import com.unicamp.medalseats.payment.PayableId
import com.unicamp.medalseats.payment.PayerId
import com.unicamp.medalseats.payment.PaymentId
import com.unicamp.medalseats.payment.PaymentMethodId
import com.unicamp.medalseats.payment.ReceiverId
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.money.MonetaryAmount

data class AuthorizePaymentCommand(
    val aggregateId: PaymentId,
    val createdOn: Instant,
    val metadata: ImmutableMap<String, String>,
    val payer: Payer,
    val invoice: Invoice,
    val source: PaymentSource,
    val amount: MonetaryAmount,
    val additionalData: ImmutableMap<String, String>,
) {
    data class Invoice(
        val id: InvoiceId,
        val proposedAt: Instant,
        val expiresAt: Instant,
        val paymentPreparedAt: Instant,
        val payables: ImmutableList<Payable>,
    ) {
        suspend fun isExpired() = expiresAt <= Clock.System.now()

        data class Payable(
            val id: PayableId,
            val receiver: Receiver,
            val type: String,
            val amount: MonetaryAmount
        ) {
            data class Receiver(
                val id: ReceiverId,
                val type: String,
                val sequentialId: Long?,
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
        }
    }

    data class Payer(
        val id: PayerId,
        val type: String,
        val name: String,
        val document: Document?
    ) {
        data class Document(
            val number: String,
            val type: String
        )
    }

    sealed interface PaymentSource {

        val paymentMethod: PaymentMethod
        val additionalData: ImmutableMap<String, String>
        val type: PaymentSourceType

        enum class PaymentSourceType {
            PIX,
        }

        data class PaymentMethod(
            val id: PaymentMethodId,
            val name: String,
            val type: String,
            val method: String,
            val liability: String,
            val brand: Brand,
        ) {
            data class Brand(
                val id: BrandId,
                val name: String
            )
        }

        data class PixSource(
            override val paymentMethod: PaymentMethod,
            override val additionalData: ImmutableMap<String, String>
        ) : PaymentSource {

            override val type: PaymentSourceType = PaymentSourceType.PIX
        }
    }
}
