package org.solyton.solawi.bid.module.bid

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import org.solyton.solawi.bid.module.bid.action.db.BidBests.BidProcessSetup
import org.solyton.solawi.bid.module.db.schema.Auction
import org.solyton.solawi.bid.module.db.schema.AuctionBidders
import org.solyton.solawi.bid.module.db.schema.Bidder
import org.solyton.solawi.bid.module.db.schema.Round

fun Transaction.setupBidProcess(): BidProcessSetup {
    // db setup
    // create an auction
    val auction = Auction.new {
        name = "TestAuction"
        date = DateTime().withDate(1,1,1)
    }
    // create a round in the auction
    // note: state is "OPENED" by default.
    val round = Round.new {
        this.auction = auction
        link = "test-link"
        // state = RoundState.Started.toString()
    }
    auction.rounds+round
    // invite bidders to the auction
    val bidder = Bidder.new {
        username = "test-user"
        weblingId = 1
        numberOfParts = 1
    }
    AuctionBidders.insert{
        it[AuctionBidders.auctionId] = auction.id
        it[AuctionBidders.bidderId] = bidder.id
    }
    auction.bidders+bidder

    return BidProcessSetup(auction, round, bidder)
}