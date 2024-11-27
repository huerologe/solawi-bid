package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.language.*
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.data.modals
import org.solyton.solawi.bid.module.bid.component.AuctionList
import org.solyton.solawi.bid.module.bid.component.showAuctionModal
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.error.lang.operationNotImplementedTexts
import org.solyton.solawi.bid.module.i18n.data.language

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionPage(storage: Storage<Application>) = Div{
    Button(attrs = {
        onClick {
            (storage * modals).showAuctionModal(
                ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.createDialog")
            ) {
                (storage * modals).showErrorModal(
                    operationNotImplementedTexts
                )
            }
        }
    } ){
        Text("Create Auction")
    }
    // render list of auctions
    // each entry offers the opportunity to read details, edit, or delete
    AuctionList(storage * auctions)

}