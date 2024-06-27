package com.medalseats.application.command.account

import com.medalseats.application.command.account.CreateAccountCommandHandlerTestFixture.VALID_EMAIL
import com.medalseats.application.command.account.CreateAccountCommandHandlerTestFixture.account
import com.medalseats.application.command.account.CreateAccountCommandHandlerTestFixture.command
import com.unicamp.medalseats.CryptographyService
import com.unicamp.medalseats.account.AccountRepository
import com.unicamp.medalseats.account.exception.AccountException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkObject
import io.mockk.unmockkStatic
import kotlinx.datetime.Clock
import java.util.UUID

class CreateAccountCommandHandlerTest : DescribeSpec({

    // Especificação explícita dos tipos dos mocks
    val accountRepository: AccountRepository = mockk()
    val cryptographyService: CryptographyService = mockk()

    val createAccountCommandHandler = CreateAccountCommandHandler(
        accountRepository = accountRepository,
        cryptographyService = cryptographyService
    )

    beforeTest {
        val uuid = UUID.randomUUID()
        val clock = Clock.System.now()
        mockkStatic(UUID::class)
        mockkObject(Clock.System)
        every { UUID.randomUUID() } returns uuid
        every { Clock.System.now() } returns clock
    }

    afterTest {
        clearMocks(accountRepository, cryptographyService)
        unmockkStatic(UUID::class)
        unmockkObject(Clock.System)
    }

    describe("Test create account command handler") {
        context("When email already exists") {
            it("Should throw AccountConflictException") {
                val existingAccount = account()
                coEvery { accountRepository.findByEmail(VALID_EMAIL) } returns existingAccount

                shouldThrow<AccountException.AccountConflictException> {
                    createAccountCommandHandler.handle(command())
                }

                coVerify { accountRepository.findByEmail(command().email) }
                confirmVerified(accountRepository)
            }
        }

        context("When email is unique") {
            it("Should register account successfully") {
                coEvery { accountRepository.findByEmail(VALID_EMAIL) } returns null
                coEvery { cryptographyService.encrypt(CreateAccountCommandHandlerTestFixture.VALID_PASSWORD) } returns "encryptedPassword"

                createAccountCommandHandler.handle(command())

                coVerify { accountRepository.register(match {
                    it.email == VALID_EMAIL &&
                            it.password == "encryptedPassword"
                }) }
            }
        }

        context("Password encryption") {
            it("Should encrypt password correctly") {
                coEvery { cryptographyService.encrypt(CreateAccountCommandHandlerTestFixture.VALID_PASSWORD) } returns "encryptedPassword"

                val encryptedPassword = cryptographyService.encrypt(CreateAccountCommandHandlerTestFixture.VALID_PASSWORD)

                encryptedPassword shouldBe "encryptedPassword"
            }
        }
    }
})
