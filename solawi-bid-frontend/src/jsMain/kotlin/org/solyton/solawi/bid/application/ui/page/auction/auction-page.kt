package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionPage(storage: Storage<Application>, auctionId: String) = Div{

    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }

    val auction = storage * auctions * FirstBy{ it.auctionId == auctionId }

    H1 { Text( auction.read().name ) }

    // Show details:
    // - Date
    // - Benchmark
    // - Solidarity ...
    // - Target Amount

    Button(attrs = {
        onClick {

        }
    }) { Text("Import Bidders") }

    H2 { Text("Rounds") }

    // Show list of rounds ordered by date - descending

    // Each list item shall contain
    // a crypto link,
    // the state of the round
    // a button "next state" (start, stop, evaluate, ...)
    // a link to the evaluation page
    // a link to the details of the round



}