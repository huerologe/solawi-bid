package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.data.environment
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions
import org.solyton.solawi.bid.module.bid.data.link
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.bid.data.state
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

// todo improve data transfer to page
@Markup
@Composable
@Suppress("FunctionName")
fun RoundPage(storage: Storage<Application>, /*round: Storage<Round>*/auctionId: String, roundId: String) = Div{

    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }


    val auction = storage * auctions * FirstBy { it.auctionId == auctionId }
    val round = auction * rounds * FirstBy { it.roundId == roundId }
    val link = round * link
    val state = round * state

    val frontendBaseUrl = with((storage * environment).read()){
        "$frontendUrl:$frontendPort"
    }
    val fullLink = "$frontendBaseUrl/bid/send/${link.read()}"

    H1 { Text("Round Page") }
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
        }
    }
    ) {
        Div(attrs = {
            style {
                width(80.percent)
            }
        }){
            Text("left")
        }
        QRCodeSvg(fullLink, download = true)
    }
}