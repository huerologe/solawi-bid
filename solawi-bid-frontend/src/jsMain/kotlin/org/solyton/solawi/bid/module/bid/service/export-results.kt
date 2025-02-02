package org.solyton.solawi.bid.module.bid.service

import org.solyton.solawi.bid.module.bid.data.api.BidRoundResults

fun BidRoundResults.toCsvContent(): String = """
    |Email;Anteile;Gebot
    |${results.joinToString("\n") { with(it){"$username;$numberOfShares;$amount"} }}
""".trimMargin()

