package org.solyton.solawi.bid.module.bid.action.db

import kotlinx.coroutines.coroutineScope
import org.evoleq.exposedx.transaction.resultTransaction
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.bindSuspend
import org.evoleq.math.MathDsl
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.Transaction
import org.solyton.solawi.bid.module.bid.data.api.AuctionDetails
import org.solyton.solawi.bid.module.bid.data.api.BidResult
import org.solyton.solawi.bid.module.bid.data.api.BidRoundResults
import org.solyton.solawi.bid.module.bid.data.api.ExportBidRound
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*

@MathDsl
val ExportResults = KlAction<Result<ExportBidRound>, Result<BidRoundResults>> {
    roundData -> DbAction {
        database -> coroutineScope { roundData bindSuspend {data -> resultTransaction(database) {
            getResults(UUID.fromString(data.auctionId),UUID.fromString(data.roundId))
        } } } x database
    }
}


fun Transaction.getResults(auctionId: UUID, roundId: UUID):BidRoundResults {
    // Collect auxiliary data
    val auction = AuctionEntity.find {
        AuctionsTable.id eq auctionId
    }.firstOrNull()?: throw Exception()

    val bidderDetails = getBidders(auction).map { it as BidderDetails.SolawiTuebingen }

    // Compute results for those who have sent bids
    val bidResults = BidRoundEntity.find {
        BidRoundsTable.round eq roundId
    }.map{
        BidResult(
            it.bidder.username,
            bidderDetails.first { details -> details.bidder.username == it.bidder.username }.numberOfShares,
            it.amount,
            true
        )
    }

    // Compute results for those who did not sent bids
    val auctionDetails = getAuctionDetails(auction)
    val defaultAmount = when(auctionDetails) {
        is AuctionDetails.SolawiTuebingen -> auctionDetails.benchmark + auctionDetails.solidarityContribution
        is AuctionDetails.Empty -> 0.0
    }

    val bidResultUsernames = bidResults.map{it.username}

    val defaultBids = auction.bidders
        .filter { bidder -> !bidResultUsernames.contains( bidder.username) }
        .map {
            BidResult(
                it.username,
                bidderDetails.first { details -> details.bidder.username == it.username }.numberOfShares,
                //it.numberOfShares,
                defaultAmount,
                false
            )
        }

    // Return combined results
    return BidRoundResults(
        roundId.toString(),
        listOf(
            *bidResults.toTypedArray(),
            *defaultBids.toTypedArray()
        )
    )

}

