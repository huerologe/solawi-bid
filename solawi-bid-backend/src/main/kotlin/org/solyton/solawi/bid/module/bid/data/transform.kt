package org.solyton.solawi.bid.module.bid.data

import org.solyton.solawi.bid.module.bid.data.api.Auction
import org.solyton.solawi.bid.module.bid.data.api.BidRound
import org.solyton.solawi.bid.module.bid.data.api.Round

import org.solyton.solawi.bid.module.db.schema.Auction as AuctionEntity
import org.solyton.solawi.bid.module.db.schema.Round as RoundEntity
import org.solyton.solawi.bid.module.db.schema.BidRound as BidRoundEntity


fun AuctionEntity.toApiType(): Auction = Auction(
    id = id.value.toString(),
    name = name,
    rounds = rounds.map { it.toApiType() },
    bidderIds = bidders.map { it.id.value.toString() }
)

fun RoundEntity.toApiType(): Round = Round(
    id.value.toString(),
    link,
    state
)

fun BidRoundEntity.toApiType(): BidRound = BidRound(
    id.value.toString(),
    round.toApiType(),
    auction.toApiType(),
    amount,

)



