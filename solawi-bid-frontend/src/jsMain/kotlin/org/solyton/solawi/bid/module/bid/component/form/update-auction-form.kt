package org.solyton.solawi.bid.module.bid.component.form

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.date.format
import org.evoleq.compose.date.parse
import org.evoleq.compose.label.Label
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.ModalData
import org.evoleq.compose.modal.ModalType
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.language.Locale
import org.evoleq.math.onIsDouble
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.DateInput
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.TextInput
import org.solyton.solawi.bid.application.ui.style.form.*
import org.solyton.solawi.bid.module.bid.data.*
import org.solyton.solawi.bid.module.bid.service.onNullEmpty
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun UpdateAuctionModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    auction: Storage<Auction>,
    cancel: ()->Unit,
    update: ()->Unit
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    modals,
    onOk = {
        update()
    },
    onCancel = {
        cancel()
    },
    texts = texts
) {

    Div(attrs = {style { formStyle() }}) {

        Div(attrs = {style { fieldStyle() }}) {
            Label("Name", id = "name" , labelStyle = formLabelStyle)
            TextInput((auction * name).read()) {
                id("name")
                style { textInputStyle() }
                onInput { (auction * name).write(it.value) }
            }
        }
        Div(attrs = {style { fieldStyle() }}) {
            Label("Datum", id = "date" , labelStyle = formLabelStyle)
            DateInput((auction * date).read().format(Locale.Iso)) {
                id("date")
                style { dateInputStyle() }
                onInput {
                    (auction * date).write(it.value.parse(Locale.Iso))
                }
            }
        }

        Div(attrs = {style { fieldStyle() }}) {
            Label("Benchmark", id = "benchmark" , labelStyle = formLabelStyle)
            TextInput (onNullEmpty((auction * auctionDetails * benchmark).read()){it}) {
                id("benchmark")
                style { numberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * benchmark).write(toDouble())
                    }
                }
            }
        }

        Div(attrs = {style { fieldStyle() }}) {
            Label("Target Amount", id = "targetAmount" , labelStyle = formLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * targetAmount).read()){it}) {
                id("targetAmount")
                style { numberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * targetAmount).write(toDouble())
                    }
                }
            }
        }
        Div(attrs = {style { fieldStyle() }}) {
            Label("Solidarity Contribution", id = "solidarityContribution" , labelStyle = formLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * solidarityContribution).read()){it}) {
                id("solidarityContribution")
                style { numberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * solidarityContribution).write(toDouble())
                    }
                }
            }
        }
        Div(attrs = {style { fieldStyle() }}) {
            Label("Minimal Bid", id = "minimalBid" , labelStyle = formLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * minimalBid).read()){it}) {
                id("minimalBid")
                style { numberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * minimalBid ).write(toDouble())
                    }
                }
            }
        }
    }
}

@Markup
fun Storage<Modals<Int>>.showUpdateAuctionModal(
    auction: Storage<Auction>,
    texts: Lang.Block,
    cancel: ()->Unit,
    update: ()->Unit
) = with(nextId()) {
    put(this to ModalData(
        ModalType.Dialog,
        UpdateAuctionModal(
            this,
            texts,
            this@showUpdateAuctionModal,
            auction,
            cancel,
            update
        )
    )
    )
}
