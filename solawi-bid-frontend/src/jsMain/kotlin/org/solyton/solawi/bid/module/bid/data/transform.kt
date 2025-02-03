package org.solyton.solawi.bid.module.bid.data

import kotlinx.datetime.LocalDate
import org.solyton.solawi.bid.module.bid.data.api.*
import org.solyton.solawi.bid.module.bid.data.BidResult


fun ApiAuctions.toDomainType(): List<Auction> = list.map { auction -> auction.toDomainType() }

fun ApiRound.toDomainType(): Round = Round(id, link, state)

fun ApiBidRound.toDomainType(): BidRound = BidRound(
    id,
    round.toDomainType(),
    auction.toDomainType(),
    amount
)

fun ApiAuction.toDomainType(): Auction = Auction(
    auctionId = id,
    name = name,
    date = with(date){ LocalDate(year, monthNumber, dayOfMonth) },
    rounds = rounds.map { round -> round.toDomainType() },
    bidderIds = bidderIds,
    auctionDetails = auctionDetails.toDomainType()
)



fun ApiAuctionDetails.toDomainType(): AuctionDetails  = when(this) {
    is ApiAuctionDetailsSolawiTuebingen -> AuctionDetails(
        minimalBid,
        benchmark,
        targetAmount,
        solidarityContribution
    )
    else -> AuctionDetails()
}

fun ApiBidRoundResults.toDomainType(startDownloadOfBidRoundResults: Boolean = true): BidRoundResults = BidRoundResults(
    results.map { it.toDomainType() },
    startDownloadOfBidRoundResults
)

fun ApiBidResult.toDomainType(): BidResult = BidResult(
    username,
    numberOfShares,
    amount,
    hasPlacedBid
)
