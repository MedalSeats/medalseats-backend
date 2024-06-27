package com.unicamp.medalseats.payment.exception

import com.unicamp.medalseats.payment.PaymentId

open class PaymentException(message: String) : Exception(message) {

    class PaymentNotFoundException(
        val id: PaymentId
    ) : PaymentException(
        "Payment $id not found"
    )
}
