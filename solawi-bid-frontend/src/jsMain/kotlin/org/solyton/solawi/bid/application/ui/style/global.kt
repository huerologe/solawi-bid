package org.solyton.solawi.bid.application.ui.style

import org.jetbrains.compose.web.css.*

object GlobalStyles : StyleSheet() {
    init {
        "*" style {
            margin(0.px)
            padding(0.px)
            boxSizing("border-box")
        }

        "html, body" style {
            margin(0.px)
            padding(0.px)
            height(100.vh)
            width(100.vw)
            overflow("hidden")
        }
    }
}
