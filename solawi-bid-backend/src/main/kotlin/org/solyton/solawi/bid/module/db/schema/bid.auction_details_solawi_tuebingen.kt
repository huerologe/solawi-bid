package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

typealias AuctionDetailsSolawiTuebingenEntity = AuctionDetailsSolawiTuebingen

object AuctionDetailsSolawiTuebingenTable : UUIDTable("auction_details_solawi_tuebingen") {
    val auctionId = uuid("auction_id")
    val benchmark = double("benchmark")
    val targetAmount = double("target_amount")
    val solidarityContribution = double("solidarity_contribution")
    val defaultBid = double("default_bid")
}

class AuctionDetailsSolawiTuebingen(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AuctionDetailsSolawiTuebingen>(AuctionDetailsSolawiTuebingenTable)

    var benchmark by AuctionDetailsSolawiTuebingenTable.benchmark
    var targetAmount by AuctionDetailsSolawiTuebingenTable.targetAmount
    var solidarityContribution by AuctionDetailsSolawiTuebingenTable.solidarityContribution
    var defaultBid by AuctionDetailsSolawiTuebingenTable.defaultBid
    var auction by Auction referencedOn AuctionDetailsSolawiTuebingenTable.auctionId
}

