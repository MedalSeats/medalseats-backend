package com.medalseats.application.command.account

import com.unicamp.medalseats.CryptographyService
import com.unicamp.medalseats.account.AccountRepository
import com.unicamp.medalseats.account.exception.AccountException

class SignInAccountCommandHandler(
    private val accountRepository: AccountRepository,
    private val cryptographyService: CryptographyService
) {
    suspend fun handle(command: SignInAccountCommand): Boolean {
        val account = accountRepository.findByEmail(command.email)
            ?: throw AccountException.AccountEmailNotFoundException(command.email)
        return cryptographyService.validate(
            plainText = command.password,
            cipherText = account.password
        )
    }
}
