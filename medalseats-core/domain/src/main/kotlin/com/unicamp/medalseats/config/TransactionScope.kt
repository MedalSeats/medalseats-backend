package com.unicamp.medalseats.config

interface TransactionScope {

    suspend fun <T> execute(block: suspend () -> T): T
}
