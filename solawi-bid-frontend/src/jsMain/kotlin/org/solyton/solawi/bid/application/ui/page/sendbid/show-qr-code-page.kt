package org.solyton.solawi.bid.application.ui.page.sendbid

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Vertical
import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.vw
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.device.mediaType
import org.solyton.solawi.bid.application.data.deviceData
import org.solyton.solawi.bid.application.data.environment
import org.solyton.solawi.bid.module.control.button.StdButton
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

@Markup
@Composable
@Suppress("FunctionName")
fun ShowQRCodePage(storage: Storage<Application>, cryptoPartOfLink: String) {
    val frontendBaseUrl = with((storage * environment).read()){
        "$frontendUrl:$frontendPort"
    }
    val fullLink = "$frontendBaseUrl/bid/send/$cryptoPartOfLink"

    Vertical {


        QRCodeSvg(
            size = 90.vw,
            id = "does-not-matter-in-this-context",
            data = fullLink,
            download = false
        )
        StdButton({ "Bieten" }, storage * deviceData * mediaType.get) {
            navigate("/bid/send/$cryptoPartOfLink")
        }
    }
}
