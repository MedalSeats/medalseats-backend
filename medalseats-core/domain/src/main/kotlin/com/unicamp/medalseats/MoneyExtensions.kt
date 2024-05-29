package com.unicamp.medalseats

import java.math.BigDecimal
import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount

fun String.toCurrencyUnit(): CurrencyUnit =
    Monetary.getCurrency(this)

infix fun <T : Number> T.withCurrency(currency: String): MonetaryAmount =
    Monetary.getDefaultAmountFactory()
        .setNumber(this)
        .setCurrency(currency)
        .create()

infix fun <T : Number> T.withCurrency(currency: CurrencyUnit): MonetaryAmount =
    Monetary.getDefaultAmountFactory()
        .setNumber(this)
        .setCurrency(currency)
        .create()

operator fun MonetaryAmount.plus(amount: MonetaryAmount?): MonetaryAmount =
    amount?.add(this) ?: this

fun MonetaryAmount.toBigDecimal(): BigDecimal =
    this.number.numberValueExact(BigDecimal::class.java)

fun MonetaryAmount.toBigDecimalScale(): BigDecimal =
    this.toBigDecimal().setScale(2)

fun MonetaryAmount.toDouble(): Double =
    this.number.toDouble()

fun MonetaryAmount.isValidScale(): Boolean =
    this.number.scale <= this.currency.defaultFractionDigits
