package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.math.emit
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.modals
import org.solyton.solawi.bid.application.ui.page.auction.action.createRound
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.auctionDetails
import org.solyton.solawi.bid.module.bid.data.reader.areNotConfigured
import org.solyton.solawi.bid.module.bid.data.reader.biddersHaveNotBeenImported
import org.solyton.solawi.bid.module.bid.data.reader.existsRunning
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts

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
        val isDisabled = (storage * auction * rounds * existsRunning).emit() ||
            (storage * auction * auctionDetails * areNotConfigured).emit() ||
            (storage * auction * biddersHaveNotBeenImported).emit()
        if(isDisabled) disabled()
        onClick {
            if(isDisabled) return@onClick
            CoroutineScope(Job()).launch {
                val actions = (storage * actions).read()
                try {
                    actions.emit(createRound(auction))
                } catch (exception: Exception) {
                    (storage * modals).showErrorModal(
                        errorModalTexts(
                            exception.message ?: exception.cause?.message ?: "Cannot Emit action 'CreateRound' in update mode"
                        )
                    )
                }
            }
        }
    }) {
        // todo:i18n
        Text("Create new Round")
    }
}
