package org.solyton.solawi.bid.module.bid.component.modal

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Property
import org.evoleq.compose.layout.ReadOnlyProperty
import org.evoleq.compose.layout.Vertical
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.ModalData
import org.evoleq.compose.modal.ModalType
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.bid.data.BidRound
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun SuccessfulBidInformationModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    storage: Storage<Application>,
    round: Lens<Application, BidRound>,
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
    // todo:i18n
    val bid = (storage * round).read()
    Text("Successful Bid")
    Vertical {
        ReadOnlyProperty(Property("Bid", bid.bidAmount!!))
        ReadOnlyProperty(Property("NumberOfShares", bid.numberOfShares!!))
    }
}

@Markup
fun Storage<Modals<Int>>.showSuccessfulBidInformationModal(
    storage: Storage<Application>,
    round: Lens<Application, BidRound>,
    texts: Lang.Block,

    cancel: ()->Unit,
    update: ()->Unit
) = with(nextId()) {
    put(this to ModalData(
        ModalType.Dialog,
        SuccessfulBidInformationModal(
            this,
            texts,
            this@showSuccessfulBidInformationModal,
            storage,
            round,
            cancel,
            update
        )
    ) )
}