package org.solyton.solawi.bid.module.bid.data.api

// data hosted in other module (solawi-bid-api-data)

import kotlinx.serialization.Serializable
import org.evoleq.serializationx.UUIDSerializer
import java.util.*

//import com.benasher44.uuid.Uuid as UUID

/*
@Serializable
data class Bid(
    val username: String,
    val link: String,
    val amount: Double
)

@Serializable
data class NewBidder(
    val username: String,
    val weblingId: Int,
    val numberOfParts: Int
) {
    init {
       // if(numberOfParts < 0) throw BidRoundException.IllegalNumberOfParts(numberOfParts)
    }
}

@Serializable
data class Bidder(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String,
    val weblingId: Int,
    val numberOfParts: Int,
    val bidRounds: List<BidRound> = listOf()
) {
    init {
       // if(numberOfParts < 0) throw BidRoundException.IllegalNumberOfParts(numberOfParts)
    }
}

@Serializable
data class  PreRound(
    @Serializable(with = UUIDSerializer::class)
    val auctionId: UUID,
)

@Serializable
data class  ChangeRoundState(
    @Serializable(with = UUIDSerializer::class)
    val roundId: UUID,
    val state: String
) {
    init {
     //   if(!state.isValidRoundStateName()) throw BidRoundException.NoSuchRoundState(state)
    }
}

@Serializable
data class Round(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val link: String,
    val state: String
)

@Serializable
data class PreAuction(
    val name: String
)

@Serializable
data class Auction(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val rounds: List<Round> = listOf(),
    val bidderIds: List<@Serializable(with = UUIDSerializer::class) UUID> = listOf()
)

@Serializable
data class BidRound(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID, //= ZeroUUID,
    val round: Round,
    val auction: Auction,
    val amount: Double?
)
*/
