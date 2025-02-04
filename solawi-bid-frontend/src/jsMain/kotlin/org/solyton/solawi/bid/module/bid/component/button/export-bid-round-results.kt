package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.modals
import org.solyton.solawi.bid.application.ui.page.auction.action.exportBidRoundResults
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts

@Markup
@Composable
@Suppress("FunctionName")
fun ExportBidRoundResultsButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>,
    round: Round
) {
    // todo:refactor:extract
    Button(attrs= {
        onClick {
            CoroutineScope(Job()).launch {
                val actions = (storage * actions).read()
                try {
                    actions.emit( exportBidRoundResults(
                        (storage * auction).read().auctionId,
                        auction * rounds * FirstBy { it.roundId == round.roundId })
                    )
                } catch(exception: Exception) {
                    (storage * modals).showErrorModal(
                        errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'ExportBidRound'")
                    )
                }
            }
        }
    }) {
        // todo:i18n
        Text("Export")
    }
}
