package org.solyton.solawi.bid.module.bid.action.db

import kotlinx.coroutines.coroutineScope
import org.evoleq.exposedx.transaction.resultTransaction
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.bindSuspend
import org.evoleq.math.MathDsl
import org.evoleq.math.x
import org.evoleq.util.DbAction
import org.evoleq.util.KlAction
import org.jetbrains.exposed.sql.Transaction
import org.solyton.solawi.bid.module.bid.data.api.BidResult
import org.solyton.solawi.bid.module.bid.data.api.BidRoundResults
import org.solyton.solawi.bid.module.db.schema.BidRoundEntity
import org.solyton.solawi.bid.module.db.schema.BidRoundsTable
import java.util.*

@MathDsl
val ExportResults = KlAction<Result<UUID>, Result<BidRoundResults>> {
    roundState -> DbAction {
        database -> coroutineScope { roundState bindSuspend {state -> resultTransaction(database) {
            getResults(state)
        } } } x database
    }
}


fun Transaction.getResults(roundId: UUID):BidRoundResults {
    val bidResults = BidRoundEntity.find {
        BidRoundsTable.round eq roundId
    }.map{
        BidResult(
            it.bidder.username,
            it.bidder.numberOfShares,
            it.amount
        )
    }
    return BidRoundResults(
        roundId.toString(),
        bidResults
    )

}