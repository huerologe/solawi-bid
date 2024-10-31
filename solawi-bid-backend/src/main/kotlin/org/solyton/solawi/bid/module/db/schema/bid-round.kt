package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object BidRounds : UUIDTable("bid_round") {
    val bidder = reference("bidder_id", Bidders)
    val auction = reference("auction_id", Auctions)
    val round = reference("round_id", Rounds)
    val amount = double("amount")

    init{
        index(true, auction, bidder, round)
    }
}

class BidRound(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<BidRound>(BidRounds)
    var amount by BidRounds.amount

    var auction by Auction referencedOn BidRounds.auction
    var bidder by Bidder referencedOn BidRounds.bidder
    var round by Round referencedOn BidRounds.round
}