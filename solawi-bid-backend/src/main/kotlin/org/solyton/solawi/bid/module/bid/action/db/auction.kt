package org.solyton.solawi.bid.module.bid.action.db

import org.evoleq.exposedx.transaction.resultTransaction
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.map
import org.evoleq.math.crypto.generateSecureLink
import org.evoleq.math.state.bind
import org.evoleq.math.state.map
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.solyton.solawi.bid.module.bid.data.api.*
import org.solyton.solawi.bid.module.bid.data.api.*
import org.solyton.solawi.bid.module.bid.data.toApiType
import org.solyton.solawi.bid.module.db.BidRoundException
import org.solyton.solawi.bid.module.db.schema.AuctionBidders
import org.solyton.solawi.bid.module.db.schema.Auctions
import org.solyton.solawi.bid.module.db.schema.Rounds
import java.util.*
import org.solyton.solawi.bid.module.db.schema.Auction as AuctionEntity
import org.solyton.solawi.bid.module.db.schema.Round as RoundEntity
import org.solyton.solawi.bid.module.db.schema.Bidder as BidderEntity


val CreateAuction = KlAction<PreAuction, Result<Auction>> {
    auction: PreAuction -> DbAction {
        database -> resultTransaction(database) {
            createAuction(auction.name)
        } map { it.toApiType() }x database
    }
}

fun Transaction.createAuction(name: String): AuctionEntity = AuctionEntity.new {
    this.name = name
}

val AddRound = KlAction<PreRound, Result<Round>> {
    round -> DbAction {
        database -> resultTransaction(database){
            addRound(round)
        } map {it.toApiType()}  x database
    }
}

fun Transaction.addRound(round: PreRound): RoundEntity {
    val auctionEntity = AuctionEntity.find { Auctions.id eq UUID.fromString(round.auctionId) }.first()
    val roundEntity = RoundEntity.new {
        auction = auctionEntity
    }
    roundEntity.link = generateSecureLink(round.auctionId.toString(), roundEntity.id.value.toString(), UUID.randomUUID().toString())
    return roundEntity
}

/*
val AddBidders = KlAction<List<Bidder>, Result<AuctionEntity>> = KlAction{
    list -> DbAction {
        database -> resultTransaction(database) {

        } x database
    }
}

 */



fun Transaction.addBidders(auctionId: UUID, bidders: List<NewBidder>): AuctionEntity {
    val auction = AuctionEntity.find { Auctions.id eq auctionId }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuction

    bidders.forEach { bidder ->
        val newBidder = BidderEntity.new {
            username = bidder.username
            weblingId = bidder.weblingId
            this.numberOfParts = bidder.numberOfParts
        }
        AuctionBidders.insert {
            it[AuctionBidders.auctionId] = auction.id.value
            it[AuctionBidders.bidderId] = newBidder.id.value
        }
    }

    return auction
}

val ChangeRoundState = KlAction<ChangeRoundState, Result<Round>> {
    roundState -> DbAction {
        database -> resultTransaction(database) {
            changeRoundState(roundState)
        } map { it.toApiType() } x database
    }
}

fun Transaction.changeRoundState(newState: ChangeRoundState): RoundEntity {
    val round = RoundEntity.find { Rounds.id eq UUID.fromString(newState.roundId) }.firstOrNull()
        ?: throw BidRoundException.NoSuchRound

    round.state = newState.state

    return round
}