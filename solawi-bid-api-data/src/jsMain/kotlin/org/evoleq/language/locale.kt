package org.evoleq.language

sealed class Locale(open val value: String) {
    data object De : Locale("de")
    data object En : Locale("en")

    data object Iso : Locale("iso")

    override fun toString(): String = value
}