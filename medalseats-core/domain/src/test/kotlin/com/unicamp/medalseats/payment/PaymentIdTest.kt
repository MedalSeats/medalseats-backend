package com.unicamp.medalseats.payment

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.util.UUID

class PaymentIdTest : DescribeSpec({

    describe("Test PaymentId creation") {
        beforeTest {
            val uuid = UUID.randomUUID()
            mockkStatic(UUID::class)
            every { UUID.randomUUID() } returns uuid
        }

        afterTest {
            unmockkStatic(UUID::class)
        }

        it("Should create a new random PaymentId") {
            val PaymentId = PaymentId()
            PaymentId.toUUID() shouldBe UUID.randomUUID()
        }
    }

    describe("Test PaymentId conversion") {
        context("UUID to PaymentId") {
            it("Should convert from UUID to PaymentId") {
                val uuid = UUID.randomUUID()
                uuid.toPaymentId() shouldBe PaymentId(uuid)
            }
        }

        context("String to PaymentId") {
            it("Should convert from String to PaymentId") {
                val uuid = UUID.randomUUID()
                uuid.toString().toPaymentId() shouldBe PaymentId(uuid)
            }
        }

        context("PaymentId to UUID") {
            it("Should convert PaymentId to UUID") {
                val uuid = UUID.randomUUID()
                val PaymentId = PaymentId(uuid)

                PaymentId.toUUID() shouldBe uuid
            }
        }

        context("PaymentId to String") {
            it("Should convert PaymentId to String") {
                val uuid = UUID.randomUUID()
                val PaymentId = PaymentId(uuid)

                PaymentId.toString() shouldBe uuid.toString()
            }
        }
    }
})
