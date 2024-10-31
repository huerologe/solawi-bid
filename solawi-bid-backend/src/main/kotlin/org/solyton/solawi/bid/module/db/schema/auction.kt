package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Auctions: UUIDTable("auctions") {
    val name = varchar("name", 50)
}


class Auction(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Auction>(Auctions)

    var name by Auctions.name
    val rounds by Round referrersOn Rounds.auction
    val bidders by Bidder via AuctionBidders
    val bidRounds by BidRound referrersOn BidRounds.auction
}

