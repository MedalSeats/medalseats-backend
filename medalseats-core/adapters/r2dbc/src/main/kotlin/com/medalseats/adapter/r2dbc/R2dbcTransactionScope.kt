package com.medalseats.adapter.r2dbc

import com.unicamp.medalseats.TransactionScope
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

public class R2dbcTransactionScope(private val transaction: TransactionalOperator) : TransactionScope {
    override suspend fun <T> execute(block: suspend () -> T): T {
        return transaction.executeAndAwait {
            block.invoke()
        }!!
    }
}
