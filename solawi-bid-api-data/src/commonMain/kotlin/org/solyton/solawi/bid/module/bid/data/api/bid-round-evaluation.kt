package org.solyton.solawi.bid.module.bid.data.api

import kotlinx.serialization.Serializable

typealias ApiBidRoundEvaluation = BidRoundEvaluation
typealias ApiBidRoundPreEvaluation = BidRoundPreEvaluation
typealias ApiWeightedBid = WeightedBid

@Serializable
data class EvaluateBidRound(
    val auctionId: String,
    val roundId: String
)

@Serializable
data class PreEvaluateBidRound(
    val auctionId: String,
    val roundId: String
)

@Serializable
data class BidRoundEvaluation(
    val auctionDetails: AuctionDetails,
    val totalSumOfWeightedBids: Double,
    val totalNumberOfShares: Int,
    val weightedBids: List<WeightedBid>
)

@Serializable
data class WeightedBid(
    val weight: Int,
    val bid: Double
)

@Serializable
data class BidRoundPreEvaluation(
    val auctionDetails: AuctionDetails,
    val totalNumberOfShares: Int,
)