package org.solyton.solawi.bid.module.db.schema

import org.evoleq.exposedx.test.runSimpleH2Test
import org.jetbrains.exposed.sql.insert
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.Schema
import kotlin.test.assertEquals

class AuctionTests {

    //@Schema@Test
    fun createAuction() = runSimpleH2Test(
        AuctionBidders,
        Auctions,
        Bidders,
    ) {
        val name = "TestAuction"
        val auction = Auction.new {
            this.name = name
        }

        assertEquals(name, auction.name)
    }

    //@Schema@Test
    fun addBiddersToAuction() = runSimpleH2Test(
        AuctionBidders,
        Auctions,
        Bidders,
    ) {
        val name = "TestAuction"
        val auction = Auction.new {
            this.name = name
        }

        assertEquals(name, auction.name)

        val bidder = Bidder.new {
            username = "name"
            weblingId = 1
            numberOfParts = 1
        }

        AuctionBidders.insert {
            it[auctionId] = auction.id.value
            it[bidderId] = bidder.id.value
        }

        //auction.bidders+bidder

        println(bidder.auctions.map { it.name })

        println()
    }
}