package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import io.ktor.util.*
import org.evoleq.compose.Markup
import org.evoleq.compose.routing.navigate
import org.evoleq.language.Lang
import org.evoleq.language.get
import org.evoleq.math.Reader
import org.evoleq.math.Source
import org.evoleq.math.emit
import org.evoleq.math.times
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.bid.data.api.RoundState
import org.solyton.solawi.bid.module.bid.data.auctionId
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

@Markup
@Composable
@Suppress("FunctionName")
fun QRLinkToRoundPageButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>,
    round: Round,
    frontendBaseUrl: String,
    texts: Source<Lang.Block>,

) {
    val auctionId = (storage * auction * auctionId).read()
    // todo:refactor:extract
    Button(
        attrs = {
            style {
                flexShrink(0)

            }
            onClick {
                navigate("/solyton/auctions/${auctionId}/rounds/${round.roundId}")
            }
        }
    ){
        QRCodeSvg(
            round.roundId,
            "$frontendBaseUrl/bid/send/${round.link}",
            64.0
        )
    }
}
