package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.bidRounds
import org.solyton.solawi.bid.module.bid.data.auction
import org.solyton.solawi.bid.module.bid.data.link
import org.solyton.solawi.bid.module.bid.data.round
import org.solyton.solawi.bid.module.bid.data.state
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

// todo improve data transfer to page
@Markup
@Composable
@Suppress("FunctionName")
fun BidRoundPage(storage: Storage<Application>, bidRoundId: String) = Div{


    val bidRound = storage * bidRounds * FirstBy { it.bidRoundId == bidRoundId }
    val auction = bidRound * auction
    val round =  bidRound * round
    val link = round * link
    val state = round * state

    H1 { Text("BidRoundPage") }
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
        QRCodeSvg(link.read(), download = true)
    }
}