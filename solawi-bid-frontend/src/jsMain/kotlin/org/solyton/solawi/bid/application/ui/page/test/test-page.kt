package org.solyton.solawi.bid.application.ui.page.test

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.ui.style.form.textInputStyle
import org.solyton.solawi.bid.module.authentication.data.username
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

@Markup
@Composable
@Suppress("FunctionName")
fun TestPage() {
    Text("Test Page")

    var text by remember { mutableStateOf<String>("Hello, QR Code!") }

    Div {

        TextInput(text) {
            style { textInputStyle() }
            id("qr-string")
            onInput {
                text = it.value.trim()
            }
        }


        QRCodeSvg(text)

        Button(
            attrs = {
                // onClick { saveCanvasAsImage("qrCanvas", "qrcode.png") }
                style {
                    margin(10.px)
                    padding(10.px)
                    fontSize(16.px)
                    backgroundColor(Color.black)
                    color(Color.white)
                    borderRadius(5.px)
                }
            }
        ) {
            Text("Download QR Code")
        }
    }
}