package org.solyton.solawi.bid.application.ui.page.test

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import org.solyton.solawi.bid.application.ui.style.form.textInputStyle
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


        QRCodeSvg("test-page",text, download = true)
    }
}