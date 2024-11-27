package org.solyton.solawi.bid.module.bid.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.ui.style.form.fieldStyle
import org.solyton.solawi.bid.application.ui.style.form.formLabelStyle
import org.solyton.solawi.bid.application.ui.style.form.formStyle
import org.solyton.solawi.bid.application.ui.style.form.textInputStyle
import org.w3c.dom.HTMLElement


@Markup
@Suppress("FunctionName")
fun AuctionModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    update: ()->Unit
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    modals,
    onOk = {
        update()
    },
    onCancel = null,
    texts = texts
) {

    Div(attrs = {style { formStyle() }}) {

        Div(attrs = {style { fieldStyle() }}) {
            Label("Name", id = "name" , labelStyle = formLabelStyle)
            TextInput("value") {
                id("name")
                style { textInputStyle() }
                onInput {  }
            }
        }
        Div(attrs = {style { fieldStyle() }}) {
            Label("Datum", id = "date" , labelStyle = formLabelStyle)
            DateInput() {
                id("date")
                style { textInputStyle() }
                onInput {  }
            }
        }
    }
}

@Markup
fun Storage<Modals<Int>>.showAuctionModal(
    texts: Lang.Block,
    update: ()->Unit
) = with(nextId()) {
    console.log("next id = $this")
    put(this to AuctionModal(
        this,
        texts,
        this@showAuctionModal,
        update
    ))
}
