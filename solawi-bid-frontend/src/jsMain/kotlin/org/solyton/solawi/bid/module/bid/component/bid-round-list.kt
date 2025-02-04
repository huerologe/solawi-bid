package org.solyton.solawi.bid.module.bid.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Horizontal
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.environment
import org.solyton.solawi.bid.module.bid.component.button.ChangeRoundStateButton
import org.solyton.solawi.bid.module.bid.component.button.ExportBidRoundResultsButton
import org.solyton.solawi.bid.module.bid.component.button.QRLinkToRoundPageButton
import org.solyton.solawi.bid.module.bid.component.effect.LaunchBidRoundEvaluation
import org.solyton.solawi.bid.module.bid.component.effect.LaunchDownloadOfBidRoundResults
import org.solyton.solawi.bid.module.bid.component.effect.LaunchExportOfBidRoundResults
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.bid.data.api.RoundState
import org.solyton.solawi.bid.module.bid.data.rounds

@Markup
@Composable
@Suppress("FunctionName")
fun BidRoundList(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>
) {
    H2 {
        // todo:i18n
        Text("Rounds")
    }

    val frontendBaseUrl = with((storage * environment).read()){
        "$frontendUrl:$frontendPort"
    }
    (storage * auction * rounds).read().reversed().forEach { round ->
        BidRoundListItem(
            storage = storage,
            auction = auction,
            round = round,
            frontendBaseUrl= frontendBaseUrl
        )
    }
}

@Markup
@Composable
@Suppress("FunctionName")
fun BidRoundListItem(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>,
    round: Round,
    frontendBaseUrl: String
) {
    // Effects
    when(RoundState.fromString(round.state) ) {
        is RoundState.Stopped -> LaunchExportOfBidRoundResults(
            storage = storage,
            auction = auction,
            round = round
        )
        is RoundState.Evaluated -> LaunchBidRoundEvaluation(
            storage = storage,
            auction = auction,
            round = round
        )
        is RoundState.Opened,
        is RoundState.Started,
        is RoundState.Closed,
        is RoundState.Frozen  -> Unit
    }

    // Markup
    Horizontal(styles = {width(100.percent) }) {
        LaunchDownloadOfBidRoundResults(
            storage = storage,
            auction = auction,
            round = round
        )
        QRLinkToRoundPageButton(
            storage = storage,
            auction = auction,
            round = round,
            frontendBaseUrl= frontendBaseUrl
        )
        ChangeRoundStateButton(
            storage = storage,
            auction = auction,
            round = round
        )
        ExportBidRoundResultsButton(
            storage = storage,
            auction = auction,
            round = round
        )
    }
}


