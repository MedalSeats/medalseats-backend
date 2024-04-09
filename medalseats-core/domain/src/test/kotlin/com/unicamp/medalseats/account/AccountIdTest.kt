package com.unicamp.medalseats.account

import com.unicamp.medalseats.account.AccountId
import com.unicamp.medalseats.account.toAccountId
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.util.UUID

class AccountIdTest : DescribeSpec({

    describe("Test AccountId creation") {
        beforeTest {
            val uuid = UUID.randomUUID()
            mockkStatic(UUID::class)
            every { UUID.randomUUID() } returns uuid
        }

        afterTest {
            unmockkStatic(UUID::class)
        }

        it("Should create a new random AccountId") {
            val accountId = AccountId()
            accountId.toUUID() shouldBe UUID.randomUUID()
        }
    }

    describe("Test AccountId conversion") {
        context("UUID to AccountId") {
            it("Should convert from UUID to AccountId") {
                val uuid = UUID.randomUUID()
                uuid.toAccountId() shouldBe AccountId(uuid)
            }
        }

        context("String to AccountId") {
            it("Should convert from String to AccountId") {
                val uuid = UUID.randomUUID()
                uuid.toString().toAccountId() shouldBe AccountId(uuid)
            }
        }

        context("AccountId to UUID") {
            it("Should convert AccountId to UUID") {
                val uuid = UUID.randomUUID()
                val accountId = AccountId(uuid)

                accountId.toUUID() shouldBe uuid
            }
        }

        context("AccountId to String") {
            it("Should convert AccountId to String") {
                val uuid = UUID.randomUUID()
                val accountId = AccountId(uuid)

                accountId.toString() shouldBe uuid.toString()
            }
        }
    }
})
