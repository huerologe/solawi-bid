package org.solyton.solawi.bid.module.bid.data

import kotlinx.datetime.LocalDate
import org.solyton.solawi.bid.module.bid.data.api.ApiAuctions
import org.solyton.solawi.bid.module.bid.data.api.ApiRound

fun ApiAuctions.toDomainType(): List<Auction> = list.map { auction ->
    Auction(
        auctionId = auction.id,
        name = auction.name,
        date = with(auction.date){LocalDate(year, monthNumber, dayOfMonth)},
        rounds = auction.rounds.map { round -> round.toDomainType() },
        bidderIds = auction.bidderIds
    )
}

fun ApiRound.toDomainType(): Round = Round(id, link, state)