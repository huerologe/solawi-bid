package org.solyton.solawi.bid.module.bid.service

import org.evoleq.language.Locale
import org.evoleq.math.fixDoubleString
import org.evoleq.math.isDouble
import kotlin.math.pow
import kotlin.math.roundToInt


fun Double.roundTo(precision: Int): Double = with(10.0.pow(precision)) {
    (this@roundTo * this).roundToInt().toDouble() / this
}


fun String.isDouble(locale: Locale): Boolean = when(locale){
    is Locale.En, is Locale.Iso -> Regex("^-?\\d*\\.?\\d+\$").matches(this)
    is Locale.De -> Regex("^-?\\d*,?\\d+\$").matches(this)
}

fun <T > onNullEmpty(value: T?, manipulate: (String)->String = {s -> s}): String = when{
    value == null -> ""
    else -> manipulate("$value")
}

fun String.toPrice(): String = when{
    !contains(".") -> "$this.00"
    else -> this
}