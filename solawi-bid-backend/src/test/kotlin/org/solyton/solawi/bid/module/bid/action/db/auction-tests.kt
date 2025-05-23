package org.solyton.solawi.bid.module.bid.action.db

import kotlinx.datetime.LocalDate
import org.evoleq.exposedx.test.runSimpleH2Test
import org.jetbrains.exposed.sql.selectAll
import org.joda.time.DateTime
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.DbFunctional
import org.solyton.solawi.bid.module.bid.data.api.CreateRound
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.toApiType
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class AuctionTests {

    @DbFunctional@Test fun createAuction() = runSimpleH2Test(
        AuctionBidders,
        AcceptedRoundsTable,
        Auctions,
        Bidders,
        Rounds
    ) {

    }

    @DbFunctional@Test fun prepareAuction() = runSimpleH2Test(
        AuctionBidders,
        AcceptedRoundsTable,
        AuctionDetailsSolawiTuebingenTable,
        Auctions,
        Bidders,
        BidderDetailsSolawiTuebingenTable,
        Rounds
    ) {
        val name = "TestAuction"
        val link = "TestLink"
        AuctionType.new {
            type = "SOLAWI_TUEBINGEN"
        }
        val auction = createAuction(name,LocalDate(0,1,1)).toApiType()
        assertEquals(name, auction.name)
        val round = addRound(
            CreateRound(
            auction.id,
        )
        ).toApiType()
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
        assertEquals(bidders.size, auctionWithBidders.bidderInfo.size)

    }


    @DbFunctional@Test fun addNewBiddersOfAuction() = runSimpleH2Test(
        AuctionBidders,
        AcceptedRoundsTable,
        AuctionDetailsSolawiTuebingenTable,
        Auctions,
        Bidders,
        BidderDetailsSolawiTuebingenTable,
        Rounds
    ) {
        val name = "TestAuction"
        val link = "TestLink"
        AuctionType.new {
            type = "SOLAWI_TUEBINGEN"
        }
        val auction = createAuction(name,LocalDate(0,1,1)).toApiType()

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
        assertEquals(bidders.size, auctionWithBidders.bidderInfo.size)

        val newBidders = listOf<NewBidder>(
            NewBidder("name1",1,1),
            NewBidder("name2",3,1),
            NewBidder("name5",3,1),
            NewBidder("name6",4,1)
        )

        // val auctionWithNewBidders =
        addBidders(
            auctionId = UUID.fromString( auction.id),
            newBidders
        ).toApiType()
        assertEquals(6, AuctionBidders.selectAll().count())
        assertEquals(6, Bidders.selectAll().count())
    }

    @DbFunctional@Test fun addSameBiddersToDifferentAuctions() = runSimpleH2Test(
        AuctionBidders,
        AcceptedRoundsTable,
        AuctionDetailsSolawiTuebingenTable,
        Auctions,
        Bidders,
        BidderDetailsSolawiTuebingenTable,
        Rounds
    ) {
        val name = "TestAuction"
        val link = "TestLink"
        AuctionType.new {
            type = "SOLAWI_TUEBINGEN"
        }
        val auction1 = createAuction(name, LocalDate(0, 1, 1))
        assertEquals(name, auction1.name)

        val auction2 = createAuction(name, LocalDate(0, 1, 1))
        assertEquals(name, auction2.name)


        val bidders = listOf<NewBidder>(
            NewBidder("name1", 1, 1),
            NewBidder("name2", 3, 1),
            NewBidder("name3", 3, 1),
            NewBidder("name4", 4, 1)
        )

        addBidders(auction1, bidders)
        addBidders(auction2, bidders)

        assertEquals(8, AuctionBidders.selectAll().count())
    }

    @DbFunctional@Test
    fun deleteAuction() = runSimpleH2Test(
        AuctionBidders,
        AcceptedRoundsTable,
        AuctionDetailsSolawiTuebingenTable,
        Auctions,
        Bidders,
        BidderDetailsSolawiTuebingenTable,
        Rounds
    ) {
        val auctionType = AuctionType.new {
            type = "SOLAWI_TUEBINGEN"
        }
        val auction = AuctionEntity.new {
            name = "TestAuction"
            date = DateTime().withDate(1,1,1)
            type = auctionType
        }

        deleteAuctions(listOf(auction.id.value))

        val a = AuctionEntity.find { Auctions.id eq auction.id }.firstOrNull()
        assertNull(a)
    }
}
