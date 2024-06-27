package com.medalseats.application.command.account

import com.medalseats.application.command.account.SignInAccountCommandHandlerTestFixture.VALID_EMAIL
import com.medalseats.application.command.account.SignInAccountCommandHandlerTestFixture.account
import com.medalseats.application.command.account.SignInAccountCommandHandlerTestFixture.command
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
import kotlin.math.sign

class SignInAccountCommandHandlerTest : DescribeSpec({

    val accountRepository = mockk<AccountRepository>()
    val cryptographyService = mockk<CryptographyService>()

    val signInAccountCommandHandler = SignInAccountCommandHandler(
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

    describe("Test sign in account command handler") {
        context("When email is not found") {
            it("Should throw AccountEmailNotFoundException") {
                coEvery {
                    accountRepository.findByEmail(any())
                } throws AccountException.AccountEmailNotFoundException(VALID_EMAIL)

                shouldThrow<AccountException.AccountEmailNotFoundException> {
                    signInAccountCommandHandler.handle(command())
                }

                coVerify {
                    accountRepository.findByEmail(command().email)
                }

                confirmVerified(accountRepository)
            }
        }

        context("When email is found but password is invalid") {
            it("Should return false") {
                coEvery {
                    accountRepository.findByEmail(any())
                } returns account()

                coEvery {
                    cryptographyService.validate(any(), any())
                } returns false

                signInAccountCommandHandler.handle(command()) shouldBe false

                coVerify {
                    accountRepository.findByEmail(command().email)
                }

                coVerify {
                    cryptographyService.validate(
                        plainText = command().password,
                        cipherText = account().password
                    )
                }

                confirmVerified(accountRepository, cryptographyService)
            }
        }

        context("When email is found and password is valid") {
            it("Should return true") {
                coEvery {
                    accountRepository.findByEmail(any())
                } returns account()

                coEvery {
                    cryptographyService.validate(any(), any())
                } returns true

                signInAccountCommandHandler.handle(command()) shouldBe true

                coVerify {
                    accountRepository.findByEmail(command().email)
                }

                coVerify {
                    cryptographyService.validate(
                        plainText = command().password,
                        cipherText = account().password
                    )
                }

                confirmVerified(accountRepository, cryptographyService)
            }
        }
    }
})
