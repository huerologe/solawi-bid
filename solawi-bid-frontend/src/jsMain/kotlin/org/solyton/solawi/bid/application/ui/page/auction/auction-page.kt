package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionPage(storage: Storage<Application>) = Div{
    Button(attrs = {
        onClick {
            // render create auction modal
        }
    } ){
        Text("Create Auction")
    }
    // render list of auctions
    // each entry offers the opportunity to read details, edit, or delete

}