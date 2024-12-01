package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

typealias BidderEntity = Bidder
typealias BiddersTAble = Bidders

object Bidders : UUIDTable("bidders") {

    val username = varchar("username", 100)

    // external Id in the webling interface
    val weblingId = integer("webling_id")

    // number of parts the prosumer wants to buy
    val numberOfShares = integer("number_of_shares")

    init{
        index(true, username, weblingId)
    }
}

class Bidder(id : EntityID<UUID> ) : UUIDEntity(id) {
    companion object: UUIDEntityClass<Bidder>(Bidders)

    var username by Bidders.username
    var weblingId by Bidders.weblingId
    var numberOfParts by Bidders.numberOfShares

    var auctions by Auction via AuctionBidders
    val bidRounds by BidRound referrersOn BidRounds.bidder
}