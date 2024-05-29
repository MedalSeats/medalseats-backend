package com.unicamp.medalseats

interface TransactionScope {

    suspend fun <T> execute(block: suspend () -> T): T
}
