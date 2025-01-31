package org.solyton.solawi.bid.module.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement

@JsModule("qrcode")
@JsNonModule
external object QRCode {
    fun toString(text: String, options: dynamic, callback: (error: String?, svg: String?) -> Unit)
}

@Composable
fun QRCodeSvg(data: String, size: Double = 256.0) {
    val scope = MainScope()
    var qrSvg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(data) {
        scope.launch {
            QRCode.toString(data, js("{}")) { _, svg ->
                qrSvg = svg
            }
        }
    }

    Div(attrs = {
        id("qr-container")
        style {
            width(size.px)
        }
    }) {
        qrSvg?.let { svgContent ->
            DisposableEffect(svgContent) {
                val container = document.getElementById("qr-container") as? HTMLDivElement
                container?.innerHTML = svgContent // Inject the SVG into the div
                onDispose { container?.innerHTML = "" }
            }
        }
    }
}

/*
fun main() {
    renderComposable(rootElementId = "root") {

    }
}

 */
