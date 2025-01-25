package org.solyton.solawi.bid.module.application


data object Context {

    data object Application : ValueWithDescription {
        override val value = Value.APPLICATION
        override val description = ""

        object Organization : ValueWithDescription {
            override val value = combine( Value.APPLICATION, Value.ORGANIZATION )
            override val description = ""
        }
    }

    object Organization : ValueWithDescription {
        override val value = Value.ORGANIZATION
        override val description = ""

        object Management : ValueWithDescription {
            override val value = combine( Organization.value,Value.MANAGEMENT)
            override val description = ""
        }
    }

    object Auction  : ValueWithDescription{
        override val value = Value.AUCTION
        override val description = ""

        object Management : ValueWithDescription {
            override val value = combine(Auction.value, Value.MANAGEMENT)
            override val description = ""
        }
    }
}

internal fun combine(vararg contexts: String): String = contexts.joinToString("/") { it }


object Value {
    const val ORGANIZATION = "ORGANIZATION"
    const val APPLICATION = "APPLICATION"
    const val MANAGEMENT = "MANAGEMENT"
    const val AUCTION = "AUCTION"
}