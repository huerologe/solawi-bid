package org.solyton.solawi.bid.module.bid.action.db

import io.ktor.util.*
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDate
import org.evoleq.exposedx.transaction.resultTransaction
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.bindSuspend
import org.evoleq.ktorx.result.map
import org.evoleq.math.MathDsl
import org.evoleq.math.crypto.generateSecureLink
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.joda.time.DateTime
import org.solyton.solawi.bid.module.bid.data.api.*
import org.solyton.solawi.bid.module.bid.data.toApiType
import org.solyton.solawi.bid.module.db.BidRoundException
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*
import org.solyton.solawi.bid.module.bid.data.api.Auctions as ApiAuctions
import org.solyton.solawi.bid.module.db.schema.Bidder as BidderEntity
import org.solyton.solawi.bid.module.db.schema.Round as RoundEntity

@MathDsl
val CreateAuction = KlAction<Result<CreateAuction>, Result<ApiAuction>> {
    auction: Result<CreateAuction> -> DbAction {
        database -> auction bindSuspend  { data -> resultTransaction(database) {
            println("Create auction: ${data.name}")
            createAuction(data.name, data.date).toApiType()
        } }  x database
    }
}

fun Transaction.createAuction(name: String, date: LocalDate, type: String = "SOLAWI_TUEBINGEN"): AuctionEntity {
    val auctionType = AuctionType.find { AuctionTypes.type eq type.toUpperCasePreservingASCIIRules() }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuctionType(type)

    return AuctionEntity.new {
        this.name = name
        this.date = DateTime().withDate(date.year, date.monthNumber, date.dayOfMonth)
        this.type = auctionType
    }
}

@MathDsl
val ReadAuctions = KlAction<Result<GetAuctions>, Result<ApiAuctions>> {
    _ -> DbAction { database ->  resultTransaction(database){
        val auctions = readAuctions().map {
            it.toApiType().copy(auctionDetails = getAuctionDetails(it))
        }
        ApiAuctions(auctions)


    // TODO(use identifier to return all auction which are accessible as identified person)
    } x database }
}

fun Transaction.getAuctionDetails(auction: AuctionEntity): AuctionDetails {
    val tue = AuctionDetailsSolawiTuebingen.find { AuctionDetailsSolawiTuebingenTable.auctionId eq auction.id.value }.firstOrNull()
    return if(tue != null) {
        AuctionDetails.SolawiTuebingen(
            tue.minimalBid,
            tue.benchmark,
            tue.targetAmount,
            tue.solidarityContribution
        )
    } else {
        AuctionDetails.Empty
    }
}

fun Transaction.readAuctions(): List<AuctionEntity> = with(AuctionEntity.all().map{it
}) {
   try {
       toList()
   } catch(exception: Exception)
   {
       listOf()
   }
}

@MathDsl
val ReadAuction = KlAction<Result<UUID>, Result<ApiAuction>> {
    auction -> DbAction {
        database ->  auction bindSuspend { resultTransaction(database) {
                readAuction(it).toApiType()

                // TODO(use identifier to return all auction which are accessible as identified person)
            }
        }  x database
    }}



fun Transaction.readAuction(auctionId: UUID): AuctionEntity {
    val auction = AuctionEntity.find { Auctions.id eq auctionId }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuction

    return auction
}



@MathDsl
val DeleteAuctions = KlAction<Result<DeleteAuctions>, Result<GetAuctions>> {
    auctions -> DbAction {
        database -> auctions bindSuspend {
            data -> resultTransaction(database){
                deleteAuctions(data.auctionIds.map { UUID.fromString(it) })
        } } map { GetAuctions } x database
    }
}

fun Transaction.deleteAuctions(auctionIds: List<UUID>) {
    Auctions.deleteWhere { Auctions.id inList auctionIds }
}

@MathDsl
val UpdateAuctions = KlAction<Result<UpdateAuctions>, Result<GetAuctions>> {
    auctions -> DbAction {
        database -> auctions bindSuspend {
            data -> resultTransaction(database) {
                 updateAuctions(data.list )
            }
        } map { GetAuctions } x database
    }
}

fun Transaction.updateAuctions(auctions: List<ApiAuction>) {
    TODO("Function updateAuctions not implemented yet!")
}

@MathDsl
val ConfigureAuction = KlAction<Result<ConfigureAuction>, Result<ApiAuction>> {
    auction -> DbAction {
        database -> auction bindSuspend {
            data -> resultTransaction(database) {
                configureAuction(data)
            }
        }  x database
    }
}

fun Transaction.configureAuction(auction: ConfigureAuction): ApiAuction {
    val auctionEntity = AuctionEntity.findById(UUID.fromString(auction.id))?:
        throw BidRoundException.NoSuchAuction

    val auctionDetails = setAuctionDetails(auctionEntity, auction.auctionDetails)

    return with(auctionEntity) {
        name = auction.name
        date = DateTime().withDate(auction.date.year, auction.date.monthNumber, auction.date.dayOfMonth)


        this
    }.toApiType().copy(auctionDetails = auctionDetails)
}

fun Transaction.setAuctionDetails(auction: AuctionEntity, auctionDetails: AuctionDetails): AuctionDetails =
    when(auctionDetails) {
        is AuctionDetails.Empty -> auctionDetails
        is AuctionDetails.SolawiTuebingen -> {
            val detailsEntity = AuctionDetailsSolawiTuebingen.find {
                AuctionDetailsSolawiTuebingenTable.auctionId eq auction.id.value
            }.firstOrNull()
            if(detailsEntity == null) {
                AuctionDetailsSolawiTuebingen.new {
                    this.auction = auction
                    benchmark = auctionDetails.benchmark
                    targetAmount = auctionDetails.targetAmount
                    solidarityContribution = auctionDetails.solidarityContribution
                    minimalBid = auctionDetails.minimalBid
                }
            } else {
                detailsEntity.benchmark = auctionDetails.benchmark
                detailsEntity.targetAmount = auctionDetails.targetAmount
                detailsEntity.solidarityContribution = auctionDetails.solidarityContribution
                detailsEntity.minimalBid = auctionDetails.minimalBid
            }
            auctionDetails
        }
    }


@MathDsl
val AddRound = KlAction<Result<CreateRound>, Result<Round>> {
    round -> DbAction {
        database -> round bindSuspend  { data -> resultTransaction(database){
            addRound(data).toApiType()
        } } x database
    }
}

fun Transaction.addRound(round: CreateRound): RoundEntity {
    val auctionEntity = AuctionEntity.find { Auctions.id eq UUID.fromString(round.auctionId) }.first()
    val roundEntity = RoundEntity.new {
        auction = auctionEntity
    }
    roundEntity.link = generateSecureLink(round.auctionId, roundEntity.id.value.toString(), UUID.randomUUID().toString()).signature
    return roundEntity
}

/*
val AddBidders = KlAction<List<Bidder>, Result<AuctionEntity>> = KlAction{
    list -> DbAction {
        database -> resultTransaction(database) {

        } x database
    }
}

 */

fun Transaction.addBidders(auction: AuctionEntity, newBidders: List<NewBidder>, type: String = "SOLAWI_TUEBINGEN"): AuctionEntity {
    val auctionType = AuctionType.find { AuctionTypes.type eq type.toUpperCasePreservingASCIIRules() }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuctionType(type)
    val typeName = auctionType.type.toLowerCasePreservingASCIIRules()

    // There are different kinds of newBidders to consider
    // 1. known bidders listed in Bidders and AuctionBidders
    // 2. known bidders listed only in Bidders
    // 3. bidders to be created

    // Known bidders:
    // All bidders that are already listed in Bidders
    val knownBidders = BidderEntity.find{ BiddersTable.username inList newBidders.map { it.username } }
    val knownBiddersUsernames = knownBidders.map { it.username }

    val knownBiddersToBeAddedToAuction = knownBidders.filter { bidder -> !auction.bidders.contains(bidder)  }

    // Other bidders:
    // bidders that need to be created on the fly
    val createdBidders = mutableListOf<BidderEntity>()
    newBidders.filter { !knownBiddersUsernames.contains(it.username) }.forEach { bidder ->
        val newBidder = BidderEntity.new {
            username = bidder.username
            this.type = auctionType
           // weblingId = bidder.weblingId
           // this.numberOfParts = bidder.numberOfShares
        }
        when(typeName) {
            "solawi_tuebingen" -> {
                BidderDetailsSolawiTuebingenTable.insert {
                    it[BidderDetailsSolawiTuebingenTable.bidderId] = newBidder.id.value
                    it[weblingId] = bidder.weblingId
                    it[numberOfShares] = bidder.numberOfShares
                }
            }

        }
        createdBidders.add(newBidder)
    }
    listOf(
        *knownBiddersToBeAddedToAuction.toList().toTypedArray(),
        *createdBidders.toTypedArray()
    ).forEach {
        bidder ->
        AuctionBidders.insert {
            it[AuctionBidders.auctionId] = auction.id.value
            it[AuctionBidders.bidderId] = bidder.id.value
        }
    }
    //commit()
    //val nextAuction = AuctionEntity.findById(auction.id)!!
    return auction //nextAuction
}

fun Transaction.addBidders(auctionId: UUID, bidders: List<NewBidder>): AuctionEntity {
    val auction = AuctionEntity.find { Auctions.id eq auctionId }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuction

    return addBidders(auction, bidders)
}

@MathDsl
val ChangeRoundState = KlAction<Result<ChangeRoundState>, Result<Round>> {
    roundState -> DbAction {
        database -> coroutineScope { roundState bindSuspend {state -> resultTransaction(database) {
            changeRoundState(state).toApiType()
        } } } x database
    }
}

fun Transaction.changeRoundState(newState: ChangeRoundState): RoundEntity {
    val round = RoundEntity.find { Rounds.id eq UUID.fromString(newState.roundId) }.firstOrNull()
        ?: throw BidRoundException.NoSuchRound

    val sourceState = RoundState.fromString(round.state)
    val targetState = RoundState.fromString(newState.state)

    return when(targetState == sourceState.nextState()){
         true -> {
            round.state = newState.state
            round
        }
        false -> throw RoundStateException.IllegalTransition(sourceState, targetState)
    }
}

// AcceptRound

@MathDsl
val AcceptRound = KlAction<Result<AcceptRound>, Result<AcceptedRound>> {
    roundState -> DbAction {
        database -> coroutineScope { roundState bindSuspend {data -> resultTransaction(database) {
            acceptRound(data)
        } } } x database
    }
}

fun Transaction.acceptRound(acceptRound: AcceptRound): AcceptedRound {
    val auction = AuctionEntity.find { Auctions.id eq UUID.fromString(acceptRound.auctionId) }.firstOrNull()
        ?: throw BidRoundException.NoSuchAuction

    val round = RoundEntity.find { Rounds.id eq UUID.fromString(acceptRound.roundId) }.firstOrNull()
        ?: throw BidRoundException.NoSuchRound

    auction.acceptedRound = round

    return AcceptedRound(acceptRound.roundId)
}