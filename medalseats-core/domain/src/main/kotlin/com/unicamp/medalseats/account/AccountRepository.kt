package com.unicamp.medalseats.account

interface AccountRepository {

    suspend fun findByEmail(email: String): Account?
    suspend fun register(account: Account)
}
