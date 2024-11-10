package org.solyton.solawi.bid.module.cookie

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.math.x
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Markup
@Composable
@Suppress("FunctionName")
fun CookieDisclaimer(
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
) = Div {
    console.log("haha")
    if (!isCookieDisclaimerConfirmed.read()) {
        with(modals.nextId()) {
            val id = this
            modals.put(
                id x CookieDisclaimerModal(
                    id,
                    texts,
                    modals,
                    isCookieDisclaimerConfirmed
                )
            )
        }
    }
}

@Markup
@Suppress("FunctionName")
fun CookieDisclaimerModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    isCookieDisclaimerConfirmed: Storage<Boolean>
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    modals,
    onOk = {
        isCookieDisclaimerConfirmed.write(true)
    },
    onCancel = null,
    texts = texts
) {
    Div {
        Text("Blub Cookie")
        /*
        with(texts.component("content")) {
            Text(this["hint"])
        }

         */
    }
}
