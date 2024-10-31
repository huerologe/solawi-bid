package org.solyton.solawi.bid.module.db.schema

import io.ktor.util.*
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Rounds: UUIDTable("rounds") {
    val link = varchar("link", 500).default("not-set")
    val state = varchar("state", 100).default("${RoundState.Opened}")
    val auction = reference("auction_id", Auctions)
}


class Round(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Round>(Rounds)

    var link by Rounds.link
    var state by Rounds.state
    var auction by Auction referencedOn Rounds.auction
    val bidRounds by BidRound referrersOn BidRounds.auction
}

sealed class RoundState {
    data object Opened : RoundState() {
        override fun toString(): String = "OPENED"
    }
    data object Started : RoundState(){
        override fun toString(): String = "STARTED"
    }
    data object Stopped : RoundState(){
        override fun toString(): String = "STOPPED"
    }
    data object Closed: RoundState(){
        override fun toString(): String = "CLOSED"
    }

}

val roundStates: List<RoundState> = listOf(RoundState.Opened, RoundState.Started, RoundState.Stopped, RoundState.Closed)

val roundStateNames: List<String> = roundStates.map { it.toString() }

fun String.isValidRoundStateName(): Boolean = roundStateNames.contains(this)

