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

    Div(attrs = {style { desktopFormStyle() }}) {

        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Name", id = "name" , labelStyle = desktopFormLabelStyle)
            TextInput((auction * name).read()) {
                id("name")
                style { desktopTextInputStyle() }
                onInput { (auction * name).write(it.value) }
            }
        }
        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Datum", id = "date" , labelStyle = desktopFormLabelStyle)
            DateInput((auction * date).read().format(Locale.Iso)) {
                id("date")
                style { desktopDateInputStyle() }
                onInput {
                    (auction * date).write(it.value.parse(Locale.Iso))
                }
            }
        }

        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Benchmark", id = "benchmark" , labelStyle = desktopFormLabelStyle)
            TextInput (onNullEmpty((auction * auctionDetails * benchmark).read()){it}) {
                id("benchmark")
                style { desktopNumberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * benchmark).write(toDouble())
                    }
                }
            }
        }

        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Target Amount", id = "targetAmount" , labelStyle = desktopFormLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * targetAmount).read()){it}) {
                id("targetAmount")
                style { desktopNumberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * targetAmount).write(toDouble())
                    }
                }
            }
        }
        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Solidarity Contribution", id = "solidarityContribution" , labelStyle = desktopFormLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * solidarityContribution).read()){it}) {
                id("solidarityContribution")
                style { desktopNumberInputStyle() }
                onInput {
                    onIsDouble(it.value) {
                        (auction * auctionDetails * solidarityContribution).write(toDouble())
                    }
                }
            }
        }
        Div(attrs = {style { desktopFieldStyle() }}) {
            Label("Minimal Bid", id = "minimalBid" , labelStyle = desktopFormLabelStyle)
            TextInput(onNullEmpty((auction * auctionDetails * minimalBid).read()){it}) {
                id("minimalBid")
                style { desktopNumberInputStyle() }
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
