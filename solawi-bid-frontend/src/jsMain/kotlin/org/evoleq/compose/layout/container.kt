package org.evoleq.compose.layout

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun Container(content: @Composable ElementScope<HTMLElement>.()->Unit) {
    Div({
        style {
            width(80.percent)
            marginLeft(10.percent)
            marginRight(10.percent)
        }
    }) {
        content()
    }
}