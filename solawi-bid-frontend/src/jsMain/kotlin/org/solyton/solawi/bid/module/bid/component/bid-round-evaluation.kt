package org.solyton.solawi.bid.module.bid.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Property
import org.evoleq.compose.layout.ReadOnlyProperty
import org.evoleq.compose.layout.Vertical
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.bid.data.bidRoundEvaluation
import org.solyton.solawi.bid.module.separator.LineSeparator

@Markup
@Composable
@Suppress("FunctionName")
fun BidRoundEvaluation(storage: Storage<Application>, round: Lens<Application, Round>) {

    val evaluation = (storage * round * bidRoundEvaluation).read()
    console.log("evaluation: $evaluation")
    // todo:i18n

    Vertical{
        ReadOnlyProperty(Property("Target Amount", evaluation.auctionDetails.targetAmount))
        ReadOnlyProperty(Property("Achieved Amount", evaluation.totalSumOfWeightedBids),)
        LineSeparator()
        // todo:style Dynamic Property styles based on value of the property
        ReadOnlyProperty(Property(
            "Difference",
            (evaluation.totalSumOfWeightedBids - evaluation.auctionDetails.targetAmount!!))
        )
    }
}