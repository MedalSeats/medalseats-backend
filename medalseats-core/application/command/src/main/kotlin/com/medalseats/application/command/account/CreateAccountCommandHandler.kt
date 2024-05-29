package com.medalseats.application.command.account

import com.unicamp.medalseats.CryptographyService
import com.unicamp.medalseats.account.Account
import com.unicamp.medalseats.account.AccountRepository
import com.unicamp.medalseats.account.exception.AccountException
import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchRepository
import com.unicamp.medalseats.match.toMatchId

class CreateAccountCommandHandler(
    private val accountRepository: AccountRepository,
    private val cryptographyService: CryptographyService
) {

    suspend fun handle(command: CreateAccountCommand) {
        validateCommand(command)
        val account = Account(
            id = command.aggregateId,
            name = command.name,
            email = command.email,
            birthday = command.birthday,
            password = cryptographyService.encrypt(command.password)
        )

        accountRepository.register(account);
    }

    private suspend fun validateCommand(command: CreateAccountCommand): Unit =
        with(command) {
            accountRepository.findByEmail(command.email)?.let {
                throw AccountException.AccountConflictException(aggregateId)
            }
        }

}
