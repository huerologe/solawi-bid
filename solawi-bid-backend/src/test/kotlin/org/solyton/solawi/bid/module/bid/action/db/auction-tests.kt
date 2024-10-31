package org.solyton.solawi.bid.module.bid.action.db

import org.evoleq.exposedx.test.runSimpleH2Test
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.DbFunctional
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.api.PreRound
import org.solyton.solawi.bid.module.bid.data.toApiType
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AuctionTests {

    @DbFunctional@Test fun createAuction() = runSimpleH2Test(
        Auctions
    ) {
        val name = "TestAuction"
        val auction = createAuction(name)
        assertEquals(name, auction.name)
    }

    @DbFunctional@Test fun prepareAuction() = runSimpleH2Test(
        Auctions,
        Bidders,
        AuctionBidders,
        Rounds
    ) {
        val name = "TestAuction"
        val link = "TestLink"

        val auction = createAuction(name).toApiType()
        assertEquals(name, auction.name)
        val round = addRound(PreRound(
            auction.id,
        )).toApiType()
        assertNotEquals(link, round.link)

        val bidders = listOf<NewBidder>(
            NewBidder("name1",1,1),
            NewBidder("name2",3,1),
            NewBidder("name3",3,1),
            NewBidder("name4",4,1)
        )

        val auctionWithBidders = addBidders(
            auctionId = UUID.fromString( auction.id),
            bidders
        ).toApiType()
        assertEquals(bidders.size, auctionWithBidders.bidderIds.size)

    }
}