package org.solyton.solawi.bid.module.error.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun ErrorModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    modals,
    onOk = {

    },
    onCancel = null,
    texts = texts
) {
    Div {
        Text("Hello! Something went wrong")
        /*
        with(texts.component("content")) {
            Text(this["hint"])
        }

         */
    }
}