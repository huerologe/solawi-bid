package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

typealias BidderDetailsSolawiTuebingenEntity = BidderDetailsSolawiTuebingen

object BidderDetailsSolawiTuebingenTable : UUIDTable("bidder_details_solawi_tuebingen") {

    val bidderId = uuid("bidder_id")
    // external Id in the webling interface
    val weblingId = integer("webling_id")

    // number of parts the prosumer wants to buy
    val numberOfShares = integer("number_of_shares")
}

class BidderDetailsSolawiTuebingen (id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BidderDetailsSolawiTuebingen>(BidderDetailsSolawiTuebingenTable)

    var bidderId by BidderDetailsSolawiTuebingenTable.bidderId
    var weblingId by BidderDetailsSolawiTuebingenTable.weblingId
    var numberOfShares by BidderDetailsSolawiTuebingenTable.numberOfShares

    var bidder by Bidder referencedOn BidderDetailsSolawiTuebingenTable.bidderId
}