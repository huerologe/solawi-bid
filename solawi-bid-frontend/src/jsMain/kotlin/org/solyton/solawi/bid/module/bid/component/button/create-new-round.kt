package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.math.emit
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.bid.component.effect.TriggerCreateNewRound
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.auctionDetails
import org.solyton.solawi.bid.module.bid.data.reader.areNotConfigured
import org.solyton.solawi.bid.module.bid.data.reader.biddersHaveNotBeenImported
import org.solyton.solawi.bid.module.bid.data.reader.existsRunning
import org.solyton.solawi.bid.module.bid.data.reader.roundAccepted
import org.solyton.solawi.bid.module.bid.data.rounds

@Markup
@Composable
@Suppress("FunctionName")
fun CreateNewRoundButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>
) {
    // todo:refactor:extract
    Button(attrs = {
        // New rounds can only be created when
        // 1. the auction is configured,
        // 2. the bidders have been imported and
        // 3. There are no open or running rounds
        // 4. auction has no accepted round
        val isDisabled = (storage * auction * rounds * existsRunning).emit() ||
            (storage * auction * auctionDetails * areNotConfigured).emit() ||
            (storage * auction * biddersHaveNotBeenImported).emit() ||
            (storage * auction * roundAccepted).emit()
        if(isDisabled) disabled()
        onClick {
            if(isDisabled) return@onClick
            TriggerCreateNewRound(
                storage = storage,
                auction = auction
            )
        }
    }) {
        // todo:i18n
        Text("Create new Round")
    }
}
