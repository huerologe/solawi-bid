package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.language.component
import org.evoleq.language.subComp
import org.evoleq.language.title
import org.evoleq.math.emit
import org.evoleq.math.times
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.*
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions
import org.solyton.solawi.bid.module.bid.component.AuctionList
import org.solyton.solawi.bid.module.bid.component.button.CreateAuctionButton
import org.solyton.solawi.bid.module.bid.component.form.DEFAULT_AUCTION_ID
import org.solyton.solawi.bid.module.bid.data.reader.BidComponent
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.i18n.data.language

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionsPage(storage: Storage<Application>) = Div{
    // Effect
    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }

    // Data
    val auction = auctions * FirstBy{ it.auctionId == DEFAULT_AUCTION_ID }

    // Texts
    val texts = (storage * i18N * language * component(BidComponent.AuctionsPage))

    // Markup
    H1 { Text((texts * title).emit()) }
    CreateAuctionButton(
        storage = storage,
        auction = auction,
        texts = texts * subComp("buttons") * subComp("createAuction")
    )

    AuctionList(storage * auctions, storage * i18N, storage * modals){
        CoroutineScope(Job()).launch {
            val actions = (storage * actions).read()
            try {
                actions.emit( it )
            } catch(exception: Exception) {
                (storage * modals).showErrorModal(
                    errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action '${it.name}'")
                )
            }
        }
    }
}
