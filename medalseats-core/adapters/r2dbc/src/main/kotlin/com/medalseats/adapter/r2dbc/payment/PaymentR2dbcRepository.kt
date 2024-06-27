package com.medalseats.adapter.r2dbc.payment

import com.medalseats.adapter.r2dbc.get
import com.medalseats.adapter.r2dbc.match.queries.MatchSqlQueries.sortingBy
import com.medalseats.adapter.r2dbc.orderBy
import com.medalseats.adapter.r2dbc.payment.queries.PaymentSqlQueries.insertPayment
import com.medalseats.adapter.r2dbc.payment.queries.PaymentSqlQueries.selectPayment
import com.medalseats.adapter.r2dbc.payment.queries.PaymentSqlQueries.updatePayment
import com.medalseats.adapter.r2dbc.payment.queries.PaymentSqlQueries.whereEmail
import com.medalseats.adapter.r2dbc.payment.queries.PaymentSqlQueries.whereId
import com.medalseats.adapter.r2dbc.where
import com.unicamp.medalseats.match.toMatchId
import com.unicamp.medalseats.payment.Payment
import com.unicamp.medalseats.payment.PaymentId
import com.unicamp.medalseats.payment.PaymentRepository
import com.unicamp.medalseats.payment.toPaymentId
import com.unicamp.medalseats.toBigDecimal
import com.unicamp.medalseats.withCurrency
import io.r2dbc.spi.Row
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.r2dbc.core.flow
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

class PaymentR2dbcRepository(private val db: DatabaseClient) : PaymentRepository {
    override suspend fun findById(paymentId: PaymentId) =
        db.sql(selectPayment().where(whereId(paymentId)))
            .bind("id", paymentId.toUUID())
            .map { row, _ ->
                row.toPayment()
            }.awaitSingleOrNull()

    override suspend fun findByEmail(email: String) =
        db.sql(
            selectPayment()
                .where(whereEmail(email))
                .orderBy(sortingBy("proposed_at", asc = true))
        )
            .bind("email", email)
            .map { row, _ ->
                row.toPayment()
            }.flow().toList().toImmutableList()

    override suspend fun save(payment: Payment) {
        db.sql(insertPayment())
            .bind("id", payment.id.toUUID())
            .bind("amount", payment.amount.toBigDecimal())
            .bind("currency", payment.amount.currency.currencyCode)
            .bind("matchId", payment.matchId.toUUID())
            .bind("email", payment.email)
            .bind("updatedAt", payment.updatedAt.toJavaInstant())
            .bind("proposedAt", payment.proposedAt.toJavaInstant())
            .bind("qrCode", payment.qrCode)
            .bind("status", payment.status.name)
            .await()
    }

    override suspend fun update(payment: Payment) {
        db.sql(updatePayment())
            .bind("id", payment.id.toUUID())
            .bind("amount", payment.amount.toBigDecimal())
            .bind("currency", payment.amount.currency.currencyCode)
            .bind("matchId", payment.matchId.toUUID())
            .bind("email", payment.email)
            .bind("updatedAt", payment.updatedAt.toJavaInstant())
            .bind("proposedAt", payment.proposedAt.toJavaInstant())
            .bind("qrCode", payment.qrCode)
            .bind("status", payment.status.name)
            .await()
    }

    private fun Row.toPayment() = Payment(
        id = this.get<UUID>("id").toPaymentId(),
        matchId = this.get<UUID>("match_id").toMatchId(),
        email = this.get<String>("email"),
        proposedAt = this.get<Instant>("proposed_at").toKotlinInstant(),
        updatedAt = this.get<Instant>("updated_at").toKotlinInstant(),
        status = enumValueOf(this.get<String>("status")),
        qrCode = this.get<String>("qr_code"),
        amount = this.get<BigDecimal>("amount") withCurrency this.get<String>("currency")
    )
}
