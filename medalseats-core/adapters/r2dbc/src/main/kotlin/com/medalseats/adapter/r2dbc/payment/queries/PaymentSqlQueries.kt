package com.medalseats.adapter.r2dbc.payment.queries

import com.medalseats.adapter.r2dbc.common.DefaultSqlQueries
import com.unicamp.medalseats.payment.PaymentId

object PaymentSqlQueries : DefaultSqlQueries() {

    fun selectPayment() =
        """
            SELECT
                id,
                match_id,
                email,
                status,
                proposed_at,
                updated_at,
                amount,
                currency,
                qr_code
            FROM payment
            WHERE 1 = 1
        """

    fun whereId(id: PaymentId?) =
        id?.let {
            """
                AND id = :id
            """
        }

    fun whereEmail(email: String?) =
        email?.let {
            """
                AND email = :email
            """
        }

    fun insertPayment() =
        """
            INSERT INTO payment VALUES(
                :id,
                :matchId,
                :email,
                :status,
                :proposedAt,
                :updatedAt,
                :amount,
                :currency,
                :qrCode
            )
        """

    fun updatePayment() =
        """
            UPDATE payment SET
                match_id = :matchId,
                email = :email,
                status = :status,
                proposed_at = :proposedAt,
                updated_at = :updatedAt,
                amount = :amount,
                currency = :currency,
                qr_code = :qrCode
            WHERE
            id = :id
        """
}
