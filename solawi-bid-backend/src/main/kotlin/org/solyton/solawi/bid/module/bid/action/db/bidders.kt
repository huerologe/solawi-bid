package org.solyton.solawi.bid.module.bid.action.db

import org.evoleq.exposedx.transaction.resultTransaction
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.bindSuspend
import org.evoleq.math.MathDsl
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.deleteWhere
import org.solyton.solawi.bid.module.bid.data.api.ImportBidders
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.toApiType
import org.solyton.solawi.bid.module.db.BidRoundException
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*

@MathDsl
val ImportBidders = KlAction{bidders: Result<ImportBidders> -> DbAction {
    database: Database -> bidders bindSuspend  {
        resultTransaction(database) {
            importBidders(auctionId = UUID.fromString(it.auctionId), it.bidders).toApiType()
        }
    } x database
}}

fun Transaction.importBidders(auctionId: UUID, newBidders: List<NewBidder>): AuctionEntity {
    val auction = AuctionEntity.find { Auctions.id eq auctionId }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuction

    // There are four kinds of bidders to consider
    // 1. Bidders to add
    // 2. Bidders to keep
    // 3. Bidders to be deleted from the auction only (belong to other auctions)
    // 4. Bidders to be deleted completely

    // Bidders to keep:
    // newBidders that are already listed in the auction.
    val biddersToKeep = auction.bidders.filter{ bidder -> newBidders.map { it.username }.contains(bidder.username)}

    // Bidders to be deleted from auction:
    // All bidders that are part of the auction, but not listed in newBidders
    val biddersToBeDeletedFromAuction = auction.bidders.filter { !biddersToKeep.contains(it)  }

    // Bidders to be deleted completely:
    // All bidders that
    // - are part of the auction
    // - not part of any other auction
    // - not listed in newBidders
    val biddersToBeDeletedCompletely = biddersToBeDeletedFromAuction.filter {
        it.auctions.count() == 1L
    }

    // Bidders to add:
    // All newBidders that are not listed in the auction
    // -> The rest is done by the function addBidders!
    val biddersToAdd = newBidders.filter {
        newBidder -> !biddersToKeep.map { it.username }.contains(newBidder.username)
    }

    AuctionBidders.deleteWhere { bidderId inList biddersToBeDeletedFromAuction.map { it.id } }

    BiddersTable.deleteWhere { BiddersTable.id inList biddersToBeDeletedCompletely.map { it.id } }
    BidderDetailsSolawiTuebingenTable.deleteWhere { bidderId inList biddersToBeDeletedCompletely.map { it.id }  }

    return addBidders(auction,biddersToAdd)
}

fun Transaction.getBidderDetails(bidder: Bidder): BidderDetailsEntity =
    BidderDetailsSolawiTuebingenEntity.find {
        BidderDetailsSolawiTuebingenTable.bidderId eq bidder.id.value
    }.firstOrNull()
    ?: throw BidRoundException.MissingBidderDetails

fun Transaction.getBidderDetails(auction: Auction): SizedIterable<BidderDetailsEntity> {
    val bidderIds = auction.bidders.map { it.id.value }
    val details = BidderDetailsSolawiTuebingenEntity.find {
        BidderDetailsSolawiTuebingenTable.bidderId inList bidderIds
    }
    return details
}