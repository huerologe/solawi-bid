package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.device.mediaType
import org.solyton.solawi.bid.application.data.deviceData
import org.solyton.solawi.bid.module.bid.component.effect.TriggerBidRoundEvaluation
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.control.button.StdButton

@Markup
@Composable
@Suppress("FunctionName")
fun EvaluateBidRoundButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>,
    round: Round
) {
    StdButton(
        // todo:i18n
        {"Evaluate Bid Round"},
        storage * deviceData * mediaType.get,
    ) {
        TriggerBidRoundEvaluation(
            storage = storage,
            auction = auction,
            round = round
        )
    }
}
