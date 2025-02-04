package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.math.emit
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.data.modals
import org.solyton.solawi.bid.application.ui.page.auction.action.configureAuction
import org.solyton.solawi.bid.module.bid.component.form.showUpdateAuctionModal
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.reader.existRounds
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.i18n.data.language

@Markup
@Composable
@Suppress("FunctionName")
fun UpdateAuctionButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>
) {
    Button( attrs = {
        // Auction can only be configured, if no rounds have been created
        val isDisabled = (storage * auction * rounds * existRounds).emit()
        if(isDisabled) attr("disabled", "true")
        style{
            // todo:style:button:edit
        }
        onClick {
            // open configuration dialog
            if(isDisabled) return@onClick
            (storage * modals).showUpdateAuctionModal(
                auction =  storage * auction,
                texts = ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.updateDialog"),
                cancel = {}
            ) {
                CoroutineScope(Job()).launch {
                    val action = configureAuction(auction)
                    val actions = (storage * actions).read()
                    try {
                        actions.emit( configureAuction(auction) )
                    } catch(exception: Exception) {
                        (storage * modals).showErrorModal(
                            errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action '${action.name}'")
                        )
                    }
                }
            }
        }
    } ){
        // todo:i18n
        Text("Configure")
    }
}
